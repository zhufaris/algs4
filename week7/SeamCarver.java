import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import java.awt.Color;

public class SeamCarver {

    private Color[][] colors;
    private double[][] energy;
    private double[][] energyTo;
    private int[][] edgeTo;

    private int height;
    private int width;


    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();

        colors = initColors(picture);

        energy = new double[height()][width()];
        energyTo = new double[height()][width()];
        edgeTo = new int[height()][width()];

        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                energy[i][j] = energy(j, i);
            }
        }
    }


    private Color[][] initColors(Picture picture) {
        height = picture.height();
        width = picture.width();

        Color[][] colors = new Color[height][width];

        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                colors[i][j] = picture.get(j, i);
            }
        }

        return colors;
    }

    private void initEnergyTo() {
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                if (i == 0) {
                    energyTo[i][j] = energy(j, i);
                    edgeTo[i][j] = -1;
                } else {
                    energyTo[i][j] = Double.MAX_VALUE;
                    edgeTo[i][j] = -1;
                }
            }
        }
    }

    private int computeEnergyTo() {
        assert (height() > 1);
        for (int i = 0; i < height() - 1; i++) {
            for (int j = 0; j < width(); j++) {
                relax(i, j);
            }
        }
        return minIndex(energyTo[height() - 1], width());

    }

    private int minIndex(double[] a, int length) {
        int minindex = -1;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            if (min > a[i]) {
                minindex = i;
                min = a[i];
            }
        }
        return minindex;
    }

    private void relax(int i, int j) {
        if (energyTo[i + 1][j] > energyTo[i][j] + energy[i + 1][j]) {
            energyTo[i + 1][j] = energyTo[i][j] + energy[i + 1][j];
            edgeTo[i + 1][j] = j;
        }

        if (j > 0 && energyTo[i + 1][j - 1] > energyTo[i][j] + energy[i + 1][j - 1]) {
            energyTo[i + 1][j - 1] = energyTo[i][j] + energy[i + 1][j - 1];
            edgeTo[i + 1][j - 1] = j;
        }

        if (j < width - 1 && energyTo[i + 1][j + 1] > energyTo[i][j] + energy[i + 1][j + 1]) {
            energyTo[i + 1][j + 1] = energyTo[i][j] + energy[i + 1][j + 1];
            edgeTo[i + 1][j + 1] = j;
        }
    }

    public Picture picture() {
        return colorsToPicture();
    }

    private Picture colorsToPicture() {
        Picture picture = new Picture(width, height);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                picture.setRGB(col, row, colors[row][col].getRGB());
            }
        }
        return picture;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public double energy(int x, int y) {
        validateXY(x, y);
        if (onEdge(x, y)) return 1000;
        return Math.sqrt(getGradient(x-1, y, x+1, y) + getGradient(x, y-1, x, y+1));
    }

    private int getGradient(int x1, int y1, int x2, int y2) {
        Color color1 = colors[y1][x1];
        Color color2 = colors[y2][x2];
        int result = 0;
        result += Math.pow(color1.getRed() - color2.getRed(), 2);
        result += Math.pow((color1.getGreen() - color2.getGreen()), 2);
        result += Math.pow((color1.getBlue() - color2.getBlue()), 2);
        return result;
    }

    private void validateX(int x) {
        if (x < 0 || x >= width())
            throw new IllegalArgumentException();
    }

    private void validateY(int y) {
        if (y < 0 || y >= height())
            throw new IllegalArgumentException();
    }

    private void validateXY(int x, int y) {
        validateX(x);
        validateY(y);
    }

    private boolean onEdge(int x, int y) {
        return (x == 0 || x == width() - 1 || y == 0 || y == height() -1);
    }

    private void transpose() {
        int temp = height;
        height = width;
        width = temp;

        double[][] energyTran = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                energyTran[i][j] = energy[j][i];
            }
        }

        Color[][] colorsTran = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                colorsTran[i][j] = colors[j][i];
            }
        }

        energy = energyTran;
        colors = colorsTran;

        energyTo = new double[height()][width()];
        edgeTo = new int[height()][width()];
    }

    public int[] findHorizontalSeam() {
        transpose();
        int[] a = findVerticalSeam();
        transpose();
        return a;
    }

    public int[] findVerticalSeam() {
        initEnergyTo();
        int min = computeEnergyTo();
        int[] seam = new int[height()];

        for (int i = height() - 1; i >= 0; i--) {
            if (i == height() - 1) {
                seam[i] = min;
            }
            else {
                seam[i] = edgeTo[i+1][seam[i+1]];
            }
        }
        return seam;
    }

    private void testSeam(int[] seam) {
        if (seam == null || seam.length != height()) throw new IllegalArgumentException();
        assert(seam.length >= 2);
        if (seam[0] < 0 || seam[0] >= width()) throw new IllegalArgumentException();
        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i+1] < 0 || seam[i+1] >= width() || Math.abs(seam[i+1] - seam[i]) > 1) throw new IllegalArgumentException();
        }
    }

    private void updateEnergy(int[] seam) {
        for (int row = 0; row < height(); row++) {
            int col = seam[row];
            if (col < 0 && col < width()) {
                energy[row][col] = energy(col, row);
            }
            col++;
            if (col < 0 && col < width()) {
                energy[row][col] = energy(col, row);
            }
        }
    }

    public void removeVerticalSeam(int[] seam) {
        testSeam(seam);
        if (width <= 1) throw new IllegalArgumentException();

        Color[][] colorscopy = new Color[height()][width()-1];
        double[][] energycopy = new double[height()][width()-1];

        for (int row = 0; row < height(); row++) {
            int index = seam[row];
            if (index + 1 < width()) System.arraycopy(colors[row], index + 1, colors[row], index, width() - 1 - index);
            System.arraycopy(colors[row], 0, colorscopy[row], 0, width() - 1);
            if (index + 1 < width()) System.arraycopy(energy[row], index + 1, energy[row], index, width() - 1 - index);
            System.arraycopy(energy[row], 0, energycopy[row], 0, width() - 1);
        }


        width--;

        updateEnergy(seam);

        colors = colorscopy;
        energy = energycopy;
    }

    public void removeHorizontalSeam(int[] seam) {
        transpose();
        removeVerticalSeam(seam);
        transpose();
    }

    public static void main(String[] args) {
        Picture picture = new Picture("C:\\Users\\faris\\OneDrive - WINSYS TECHNOLOGY PTE LTD\\Personal\\algs4\\out\\production\\algs4\\6x5.png");
        StdOut.printf("image is %d columns by %d rows\n", picture.width(), picture.height());
        //picture.show();
        SeamCarver sc = new SeamCarver(picture);
        StdOut.println(sc.energy(1,1));
        StdOut.println(sc.energy(0,1));
        StdOut.println(sc.energy(picture.width()-1, picture.height()-1));

        StdOut.printf("Displaying energy calculated for each pixel.\n");


        //SCUtility.showEnergy(sc);
        int[] vertical = sc.findVerticalSeam();
        int[] horizontal = sc.findHorizontalSeam();


        for (int i = 0; i < sc.height(); i++) {
            for (int j = 0; j < sc.width(); j++) {
                StdOut.print(" " + sc.energy[i][j]);
            }
            StdOut.println("\n");
        }

        StdOut.println("Vertical---->");
        for (int i = 0; i < vertical.length; i++) {
            StdOut.print(" " + vertical[i]);
            //StdOut.println(sc.energy(vertical[i], i));
        }

        StdOut.println();
        StdOut.println("Horizontal---->");
        for (int i = 0; i < horizontal.length; i++) {
            StdOut.print(" " + horizontal[i]);
            //StdOut.println(sc.energy(vertical[i], i));
        }

        //sc.removeVerticalSeam(vertical);
        sc.removeHorizontalSeam(horizontal);

        for (int i = 0; i < sc.height(); i++) {
            for (int j = 0; j < sc.width(); j++) {
                StdOut.print(" " + sc.energy(j, i));
            }
            StdOut.println("\n");
        }


/***
        StdOut.println("Horizontal---->");
        for (int i = 0; i < horizontal.length; i++) {
            StdOut.println("" + horizontal[i]);
            //StdOut.println(sc.energy(horizontal[i], i));
        }
 ***/
    }
}
