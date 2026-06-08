// Node.js script to generate PNG icons for the Chrome extension
// Run with: node generate-icons.js

const fs = require('fs');
const { createCanvas } = require('canvas');

function drawIcon(size) {
    const canvas = createCanvas(size, size);
    const ctx = canvas.getContext('2d');
    
    // Create gradient background
    const gradient = ctx.createLinearGradient(0, 0, size, size);
    gradient.addColorStop(0, '#4facfe');
    gradient.addColorStop(1, '#00f2fe');
    
    // Draw rounded rectangle background
    const radius = size * 0.1875;
    ctx.beginPath();
    ctx.moveTo(radius, 0);
    ctx.lineTo(size - radius, 0);
    ctx.quadraticCurveTo(size, 0, size, radius);
    ctx.lineTo(size, size - radius);
    ctx.quadraticCurveTo(size, size, size - radius, size);
    ctx.lineTo(radius, size);
    ctx.quadraticCurveTo(0, size, 0, size - radius);
    ctx.lineTo(0, radius);
    ctx.quadraticCurveTo(0, 0, radius, 0);
    ctx.closePath();
    ctx.fillStyle = gradient;
    ctx.fill();
    
    // Draw target circle
    const centerX = size / 2;
    const centerY = size * 0.39;
    const outerRadius = size * 0.1875;
    const innerRadius = size * 0.0625;
    
    // Outer circle
    ctx.beginPath();
    ctx.arc(centerX, centerY, outerRadius, 0, Math.PI * 2);
    ctx.fillStyle = 'rgba(255, 255, 255, 0.9)';
    ctx.fill();
    
    // Inner circle
    ctx.beginPath();
    ctx.arc(centerX, centerY, innerRadius, 0, Math.PI * 2);
    ctx.fillStyle = '#4facfe';
    ctx.fill();
    
    // Draw target triangle
    const triangleTop = size * 0.61;
    const triangleBottom = size * 0.844;
    const triangleLeft = size * 0.3125;
    const triangleRight = size * 0.6875;
    
    ctx.beginPath();
    ctx.moveTo(centerX, triangleTop);
    ctx.lineTo(triangleLeft, triangleBottom);
    ctx.lineTo(triangleRight, triangleBottom);
    ctx.closePath();
    ctx.fillStyle = 'rgba(255, 255, 255, 0.9)';
    ctx.fill();
    
    // Draw base
    const baseY = size * 0.859;
    const baseHeight = size * 0.0625;
    const baseRadius = size * 0.03125;
    
    ctx.beginPath();
    ctx.roundRect(size * 0.344, baseY, size * 0.3125, baseHeight, baseRadius);
    ctx.fillStyle = 'rgba(255, 255, 255, 0.8)';
    ctx.fill();
    
    return canvas;
}

// Generate icons
const sizes = [16, 48, 128];
sizes.forEach(size => {
    const canvas = drawIcon(size);
    const buffer = canvas.toBuffer('image/png');
    fs.writeFileSync(`icon${size}.png`, buffer);
    console.log(`Generated icon${size}.png`);
});

console.log('All icons generated successfully!');
