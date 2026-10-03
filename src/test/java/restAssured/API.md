# API Design — Interview Guide

A complete reference for HTTP status codes, idempotency, and the most-asked API design topics in backend interviews.

---

## Table of Contents

1. [HTTP Status Codes](#1-http-status-codes)
2. [Idempotency](#2-idempotency)
3. [PUT vs PATCH vs POST](#3-put-vs-patch-vs-post)
4. [Pagination](#4-pagination)
5. [Rate Limiting](#5-rate-limiting)
6. [API Versioning](#6-api-versioning)
7. [Authentication — JWT & OAuth2](#7-authentication--jwt--oauth2)
8. [Caching — ETag & Conditional Requests](#8-caching--etag--conditional-requests)
9. [Error Response Format](#9-error-response-format)
10. [Rapid-Fire Topics](#10-rapid-fire-topics)
11. [Common Interview Questions](#11-common-interview-questions)

---

## 1. HTTP Status Codes

### 1xx — Informational (rare in APIs)

| Code | Name | Example |
|------|------|---------|
| 100 | Continue | Client sends `Expect: 100-continue` before a large upload; server says "go ahead" |
| 101 | Switching Protocols | Upgrading an HTTP connection to WebSocket |

### 2xx — Success

| Code | Name | When to use / Example |
|------|------|----------------------|
| **200** | OK | Standard success. `GET /users/42` returns user data |
| **201** | Created | `POST /users` creates a resource. Response: `{"id": 101}` + `Location: /users/101` header |
| **202** | Accepted | Queued for async processing — `POST /reports` returns `{"job_id": "abc", "status": "pending"}` |
| **204** | No Content | Success with empty body — typical for `DELETE /users/42` |

### 3xx — Redirection

| Code | Name | When to use / Example |
|------|------|----------------------|
| **301** | Moved Permanently | Endpoint relocated permanently. `Location: /v2/users` |
| **302** | Found | Temporary redirect (common in OAuth login flows) |
| **304** | Not Modified | Valid `If-None-Match` ETag — resource unchanged, use cache |
| **307/308** | Temp/Permanent Redirect | Like 302/301 but preserves the HTTP method (POST stays POST) |

### 4xx — Client Errors

| Code | Name | Example |
|------|------|---------|
| **400** | Bad Request | Malformed request — `{"age": "banana"}` → `{"error": "age must be a number"}` |
| **401** | Unauthorized | Missing/invalid auth — no `Authorization: Bearer <token>` header |
| **403** | Forbidden | Authenticated but not allowed — non-admin hits `DELETE /admin/settings` |
| **404** | Not Found | `GET /users/9999` where user doesn't exist |
| **405** | Method Not Allowed | `DELETE /health` on a read-only endpoint |
| **406** | Not Acceptable | Client wants `Accept: application/xml`, API only serves JSON |
| **409** | Conflict | `POST /users` with an email that already exists |
| **410** | Gone | Resource permanently deleted (expired download link) |
| **412** | Precondition Failed | `If-Match` ETag doesn't match current resource version |
| **413** | Payload Too Large | 500MB upload when limit is 10MB |
| **415** | Unsupported Media Type | `Content-Type: text/plain` sent to a JSON-only endpoint |
| **422** | Unprocessable Entity | Valid JSON, semantically wrong — `end_date` before `start_date` |
| **429** | Too Many Requests | Rate limit hit. Include `Retry-After: 60` |

### 5xx — Server Errors

| Code | Name | Example |
|------|------|---------|
| **500** | Internal Server Error | Unhandled exception — null pointer, DB crash |
| **501** | Not Implemented | Endpoint stubbed out but not built |
| **502** | Bad Gateway | Gateway/proxy can't reach the upstream service |
| **503** | Service Unavailable | Maintenance or overload — include `retry_after` |
| **504** | Gateway Timeout | Upstream didn't respond in time (DB took >30s) |

### Rules of Thumb

- **Create** → `201` (not `200`)
- **Delete / PUT with no body** → `204`
- **Async job accepted** → `202`
- **Validation** → `422` for semantic errors, `400` for malformed syntax
- **401 vs 403** → 401 = "who are you?", 403 = "I know you, and no."
- **404 vs 410** → 404 = "doesn't exist", 410 = "existed, now gone forever"
- **Rate limits** → `429` + `Retry-After`

---

## 2. Idempotency

> **Definition:** Sending the same request multiple times produces the same result — no duplicate side-effects.

### Safe vs Idempotent Methods

| Method | Safe? | Idempotent? | Why |
|--------|-------|-------------|-----|
| GET | Yes | Yes | Read-only, nothing changes |
| HEAD / OPTIONS | Yes | Yes | Same |
| PUT | No | Yes | `PUT /users/42 {name:"Ada"}` — 1x or 100x, final state identical |
| DELETE | No | Yes | First call deletes (`204`), later calls `404` — but state stays "deleted" |
| **POST** | No | **No** | `POST /orders` creates a new order every time |
| PATCH | No | No* | `PATCH {views: +1}` increments each call (*depends on body semantics*) |

**Safe** = doesn't modify state. **Idempotent** = repeated calls have the same effect as one.

### The Classic Interview Scenario — Payment Retry

```
Client → POST /payments         → Server processes → Network timeout
Client retries → POST /payments → DUPLICATE CHARGE?
```

### Solution: Idempotency Key (Stripe pattern)

```http
POST /payments
Idempotency-Key: 8e03978e-40d5-43e8-bc93-6894a57f9324

{"amount": 500, "currency": "INR"}
```

- Server stores the key alongside the response.
- Retry with the same key → server returns the **stored response** without re-processing.
- Keys typically expire after ~24 hours.

**Interview answer:** *"POST is not naturally idempotent, so we make it idempotent using client-generated idempotency keys."*

---

## 3. PUT vs PATCH vs POST

```
POST  /users       → create a new resource (server assigns the ID)
PUT   /users/42    → replace the ENTIRE resource (full object required)
PATCH /users/42    → update only specific fields
```

```http
PATCH /users/42
{"email": "new@mail.com"}                  ← only email changes

PUT /users/42
{"name": "Ada", "email": "new@mail.com", "age": 30}   ← must send everything
```

**Gotcha:** PUT on a missing field may null it out — that's why PATCH exists.

---

## 4. Pagination

| Approach | Example | Trade-offs |
|----------|---------|------------|
| **Offset** | `GET /users?page=3&limit=20` | Simple, but rows inserted mid-read cause skipped/duplicated results |
| **Cursor** | `GET /users?cursor=eyJpZCI6MTAwfQ&limit=20` | Stable & scalable — used by Twitter/Slack-style feeds |

**Why cursor wins at scale:**

```sql
-- Cursor: O(1) seek with an index
WHERE id > :cursor ORDER BY id LIMIT 20

-- Offset: O(n) scan — slow on page 10,000
OFFSET 200000 LIMIT 20
```

---

## 5. Rate Limiting

**Response contract:**

```http
HTTP/1.1 429 Too Many Requests
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 0
Retry-After: 60
```

**Algorithms:**

| Algorithm | Behavior | Use case |
|-----------|----------|----------|
| Token Bucket | Tokens refill at fixed rate; allows bursts | Most APIs (AWS, Stripe) |
| Leaky Bucket | Requests processed at constant rate | Smooth traffic shaping |
| Sliding Window | Counts in a rolling window | Accurate limits |
| Fixed Window | Count resets at window boundary | Simple, but 2x burst at edges |

**Distributed systems:** implement with Redis (atomic `INCR` + `EXPIRE`) so limits hold across servers.

---

## 6. API Versioning

```
/v1/users                                ← URI versioning (most common)
Accept: application/vnd.api.v1+json     ← header/media-type versioning
?version=1                               ← query param (less common)
```

- **Breaking change** (remove/rename a field, change type) → new version
- **Non-breaking change** (add a field, add an endpoint) → same version
- Deprecate with `Sunset` / `Deprecation` headers and a migration window.

---

## 7. Authentication — JWT & OAuth2

### JWT

```
xxxxx.yyyyy.zzzzz
header.payload.signature
```

- **Header:** algorithm (`{"alg": "HS256"}`)
- **Payload:** claims (`{"sub": "user42", "exp": 1717000000}`)
- **Signature:** proves it wasn't tampered with

Stateless — the server validates the signature, no session lookup needed. Downside: hard to revoke before expiry.

### OAuth2 (interview summary)

```
Client → Authorization Server → access token
Client → API with "Authorization: Bearer <token>"
API validates → 200 or 401
```

Grant types to know: **Authorization Code** (web apps, most secure), **Client Credentials** (service-to-service), ~~Implicit~~ (deprecated).

---

## 8. Caching — ETag & Conditional Requests

```http
# First response
HTTP/1.1 200 OK
ETag: "v123"

# Next request
GET /users/42
If-None-Match: "v123"

# If unchanged:
HTTP/1.1 304 Not Modified        ← saves bandwidth
```

**Optimistic locking** with PUT:

```http
PUT /users/42
If-Match: "v123"     →  412 Precondition Failed if someone else updated it
```

Related headers: `Cache-Control: max-age=3600`, `Last-Modified` / `If-Modified-Since`.

---

## 9. Error Response Format

Use **RFC 9457** (`application/problem+json`) — a consistent error contract:

```http
HTTP/1.1 422 Unprocessable Entity
Content-Type: application/problem+json

{
  "type": "https://api.example.com/errors/validation",
  "title": "Validation failed",
  "status": 422,
  "detail": "end_date must be after start_date",
  "instance": "/orders/req-abc123"
}
```

**Principles:** never leak stack traces, include a request/trace ID for debugging, keep field names consistent.

---

## 10. Rapid-Fire Topics

| Topic | One-liner answer |
|-------|-----------------|
| **Webhooks vs Polling** | Webhooks = server pushes events to your URL (efficient). Polling = client asks repeatedly (simple, wasteful) |
| **N+1 problem** | `GET /orders` then N calls for each order's items → fix with `?include=items` or a JOIN |
| **HATEOAS** | Responses include links: `"_links": {"self": "/users/42", "orders": "/users/42/orders"}` |
| **CORS** | Browser security; server sends `Access-Control-Allow-Origin`; preflight uses `OPTIONS` |
| **Retries** | Exponential backoff + jitter (1s, 2s, 4s + random) to avoid thundering herd |
| **Circuit Breaker** | After N failures, stop calling the dead service; fail fast; probe periodically |
| **Timeouts** | Always set them — default = hung threads cascading into outages |
| **Content Negotiation** | `Accept` (format), `Accept-Language` (locale), `Accept-Encoding` (gzip) |

---

## 11. Common Interview Questions

**Q: Is DELETE idempotent if it returns 404 on retry?**
Yes. Idempotency is about *state*, not the response code — after any number of calls, the resource is gone.

**Q: 400 vs 422?**
400 = syntactically bad (invalid JSON, wrong type). 422 = syntactically valid but semantically wrong (business rule violation).

**Q: 401 vs 403?**
401 = unauthenticated (no/invalid credentials). 403 = authenticated but unauthorized (wrong permissions).

**Q: How do you make POST idempotent?**
Client sends a unique `Idempotency-Key`; server deduplicates on it and replays the stored response.

**Q: When to use 202?**
When the request is accepted but processing is async — return a job ID + status endpoint.

**Q: How do you prevent double-updates from two concurrent users?**
Optimistic locking with `ETag` + `If-Match`, or pessimistic locking at the DB level.

**Q: When does 429 fire, and what headers go with it?**
When the client exceeds its rate limit — send `Retry-After` and `X-RateLimit-*` headers.

---

*Last updated: 2026-09-25*
