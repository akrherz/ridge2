"""Generate an image."""

import click
import xml.etree.ElementTree as ET
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.colors import ListedColormap, BoundaryNorm


@click.command()
@click.option(
    "--xml-file", default="VelocityColorCurveManager.xml", help="Path to XML file"
)
@click.option("--output", default="velocity_legend.png", help="Output image file")
def main(xml_file: str, output: str):
    """Go Main Go."""

    # -----------------------------
    # Parse XML
    # -----------------------------
    tree = ET.parse(xml_file)
    root = tree.getroot()

    levels = []
    for lvl in root.findall("Level"):
        if lvl.get("type") != "double":
            continue

        lower = lvl.get("lowerValue")
        lower = float(lower) if lower is not None else 0.0
        upper = lvl.get("upperValue")
        upper = float(upper) if upper is not None else lower

        r = int(lvl.find("red").text) / 255.0
        g = int(lvl.find("green").text) / 255.0
        b = int(lvl.find("blue").text) / 255.0

        levels.append((lower, upper, (r, g, b)))

    # sort ascending
    levels.sort(key=lambda x: x[0])

    # -----------------------------
    # Build colormap
    # -----------------------------
    bounds = []
    colors = []

    for lower, upper, color in levels:
        bounds.append(lower)
        colors.append(color)

    # Add final upper bound
    bounds.append(levels[-1][1])

    cmap = ListedColormap(colors)
    norm = BoundaryNorm(bounds, cmap.N)

    vmin = bounds[0]
    vmax = bounds[-1]

    # -----------------------------
    # Figure setup
    # -----------------------------
    fig, ax = plt.subplots(figsize=(2.56, 0.32), dpi=100)
    ax.set_position((0.05, 0.55, 0.9, 0.35))
    fig.patch.set_facecolor("white")

    # Create a horizontal gradient image
    x = np.linspace(vmin, vmax, 256)
    y = np.linspace(0, 1, 10)
    X, Y = np.meshgrid(x, y)

    ax.imshow(
        X,
        aspect="auto",
        cmap=cmap,
        norm=norm,
        extent=[vmin, vmax, 0.3, 0.7],
    )

    # -----------------------------
    # Ticks every 20 kts
    # -----------------------------
    tick_start = int(np.floor(vmin / 20.0) * 20)
    tick_end = int(np.ceil(vmax / 20.0) * 20)

    ticks = np.arange(tick_start, tick_end + 1, 20)

    ax.set_xticks(ticks)
    ax.set_xlim(-40, 100)
    ax.set_yticks([])

    # Style ticks
    ax.tick_params(axis="x", colors="black", labelsize=8, length=2, width=1)

    # Optional: emphasize zero
    if vmin < 0 < vmax:
        ax.axvline(0, color="black", linewidth=1.5)

    # Remove frame except bottom
    for spine in ["top", "left", "right"]:
        ax.spines[spine].set_visible(False)

    ax.spines["bottom"].set_color("black")

    plt.savefig(output, dpi=100, facecolor=fig.get_facecolor())
    plt.close()

    print(f"Wrote {output}")


if __name__ == "__main__":
    main()
