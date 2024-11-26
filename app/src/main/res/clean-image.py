from PIL import Image
import os
import sys

def process_image(file_name):
    """
    Process a PNG file to round each pixel to its nearest color (black or white),
    while keeping transparent pixels intact.
    Args:
        file_name (str): Name of the PNG file in the same directory.
    """
    # Ensure the file exists
    if not os.path.exists(file_name):
        print(f"Error: File '{file_name}' not found.")
        return

    # Open the image
    try:
        img = Image.open(file_name).convert("RGBA")  # Ensure the image is in RGBA mode
    except Exception as e:
        print(f"Error opening image: {e}")
        return

    # Process each pixel
    def round_color(pixel):
        # If the pixel is fully transparent, return it unchanged
        if pixel[3] == 0:  # Alpha channel is 0 (transparent)
            return (0, 0, 0, 0)
        # Calculate the grayscale intensity of the pixel
        intensity = sum(pixel[:3]) / 3
        # Return white if intensity >= 128, otherwise black, preserving alpha
        return (255, 255, 255, pixel[3]) if intensity >= 128 else (0, 0, 0, pixel[3])

    # Apply the rounding function to all pixels
    pixels = img.load()
    for x in range(img.width):
        for y in range(img.height):
            pixels[x, y] = round_color(pixels[x, y])

    # Save the processed image, replacing the original
    try:
        img.save(file_name)
        print(f"Processed image saved to '{file_name}'.")
    except Exception as e:
        print(f"Error saving image: {e}")


if __name__ == "__main__":
    # Check for input argument
    if len(sys.argv) < 2:
        print("Usage: python3 process_image.py <file_name>")
        sys.exit(1)

    # File name from argument
    file_name = sys.argv[1]
    process_image(file_name)
