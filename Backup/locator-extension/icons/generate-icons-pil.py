#!/usr/bin/env python3
from PIL import Image, ImageDraw

def draw_icon(size):
    # Create a new image with RGBA mode for transparency
    img = Image.new('RGBA', (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    
    # Create gradient background (simplified as solid gradient-like colors)
    # We'll use a solid color that represents the gradient
    draw.rounded_rectangle(
        [(0, 0), (size, size)],
        radius=int(size * 0.1875),
        fill=(79, 172, 254, 255)  # #4facfe
    )
    
    # Draw target circle (outer)
    center_x = size // 2
    center_y = int(size * 0.39)
    outer_radius = int(size * 0.1875)
    draw.ellipse(
        [(center_x - outer_radius, center_y - outer_radius),
         (center_x + outer_radius, center_y + outer_radius)],
        fill=(255, 255, 255, 230)
    )
    
    # Draw target circle (inner)
    inner_radius = int(size * 0.0625)
    draw.ellipse(
        [(center_x - inner_radius, center_y - inner_radius),
         (center_x + inner_radius, center_y + inner_radius)],
        fill=(79, 172, 254, 255)
    )
    
    # Draw target triangle
    triangle_top = int(size * 0.61)
    triangle_bottom = int(size * 0.844)
    triangle_left = int(size * 0.3125)
    triangle_right = int(size * 0.6875)
    
    draw.polygon([
        (center_x, triangle_top),
        (triangle_left, triangle_bottom),
        (triangle_right, triangle_bottom)
    ], fill=(255, 255, 255, 230))
    
    # Draw base
    base_y = int(size * 0.859)
    base_height = int(size * 0.0625)
    base_width = int(size * 0.3125)
    base_x = int(size * 0.344)
    
    draw.rounded_rectangle(
        [(base_x, base_y), (base_x + base_width, base_y + base_height)],
        radius=int(size * 0.03125),
        fill=(255, 255, 255, 200)
    )
    
    return img

# Generate icons
sizes = [16, 48, 128]
for size in sizes:
    img = draw_icon(size)
    img.save(f'icon{size}.png')
    print(f'Generated icon{size}.png')

print('All icons generated successfully!')
