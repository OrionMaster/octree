package src;

public class Application {
    public static void main(String[] args) {

        int length = 600;
        StdDraw.setCanvasSize(length, length);
        StdDraw.setScale(0, length);
        double lengthInSphere = length/Math.sqrt(2);

        /** GENERATE OCTREE METHOD **/

        // Generate Octree and insert points
        Cuboid rootBoundry = new Cuboid(new Point(length/2, length/2, length/2), lengthInSphere/2, lengthInSphere/2, lengthInSphere/2);
        Octree root = new Octree(rootBoundry);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.circle(length/2,length/2,length/2);
        StdDraw.show();

        for(int i = 0; i < 800; i++) {
            double rX = Math.random() * length;
            double rY = Math.random() * length;
            double rZ = Math.random() * length;
            Point p = new Point(rX, rY, rZ);
            StdDraw.setPenColor(StdDraw.GRAY);
            StdDraw.filledCircle(p.x(), p.y(), 2);
            if (root.insert(p)) {
                StdDraw.setPenColor(StdDraw.GRAY);
                StdDraw.filledCircle(p.x(), p.y(), 2);
                StdDraw.show();
            }
        }


        /**
         SEARCH METHOD
         search for specific points in specific area range
         **/

        // specify range parameter
        double cX = length/2;
        double cY = length/2;
        double cZ = length/2;
        double halfWidth = length/2;
        double halfHeight = length/2;
        double halfDepth = length/2;


        Cuboid searchBoundary = new Cuboid(new Point(cX, cY, cZ), halfWidth, halfHeight, halfDepth);

        StdDraw.setPenColor(StdDraw.GREEN);
        Point[] selectedPoints = root.queryRange(searchBoundary).getAll();
        for(Point p : selectedPoints) {
            StdDraw.filledCircle(p.x(), p.y(), 3);
            StdDraw.show();
            System.out.println(p);
        }
    }
}
