#!/usr/bin/env python3
import easyocr
from pathlib import Path

# Set the path to the images directory
images_dir = Path("/Users/chaitanyasilawat/Desktop/code/Selenium_concepts/Photos-3-001")
output_file = Path("/Users/chaitanyasilawat/Desktop/code/Selenium_concepts/extracted_text.txt")

# Get all JPG files
image_files = sorted(images_dir.glob("*.jpg"))

print(f"Found {len(image_files)} images to process")

# Initialize EasyOCR reader
print("Initializing EasyOCR reader...")
reader = easyocr.Reader(['en'], gpu=False)
print("Reader initialized.")

# Open output file
with open(output_file, 'w', encoding='utf-8') as f:
    for idx, image_path in enumerate(image_files, 1):
        print(f"Processing {idx}/{len(image_files)}: {image_path.name}", flush=True)
        try:
            # Extract text using EasyOCR
            result = reader.readtext(str(image_path))
            
            # Combine all detected text
            text = ' '.join([detection[1] for detection in result])
            
            # Write to output file
            f.write(f"=== Image {idx}: {image_path.name} ===\n")
            f.write(text)
            f.write("\n\n")
            f.flush()  # Ensure data is written immediately
            
            print(f"  - Successfully extracted text from {image_path.name}", flush=True)
        except Exception as e:
            print(f"  - Error processing {image_path.name}: {e}", flush=True)
            f.write(f"=== Image {idx}: {image_path.name} ===\n")
            f.write(f"Error: {e}\n\n")
            f.flush()

print(f"\nText extraction complete. Output saved to: {output_file}", flush=True)
