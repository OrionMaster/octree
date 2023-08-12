package src;

public class Cuboid {
    private final double halfWidth;
    private final double halfHeight;
    private final double halfDepth;

    private final Point center;
    private double radius;

    public Cuboid(Point center, double halfWidth, double halfHeight, double halfDepth) {
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
        this.halfDepth = halfDepth;

        this.center = center;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(center.x(), center.y(), halfWidth, halfHeight);

        radius = halfHeight;
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.circle(center.x(), center.y(), radius);
    }



    public boolean contains(Point p) {
        if(!(center.x() - halfWidth < p.x() && center.x() + halfWidth > p.x())) return false;
        if(!(center.y() - halfHeight < p.y() && center.y() + halfHeight > p.y())) return false;
        return center.z() - halfDepth < p.z() && center.z() + halfDepth > p.z();
    }

    public boolean intersects (Cuboid other) {
        double otherUpperLimit = other.center.y() + other.getHalfLengths()[1];
        double otherLowerLimit = other.center.y() - other.getHalfLengths()[1];
        double otherLeftLimit =  other.center.x() - other.getHalfLengths()[0];
        double otherRightLimit =  other.center.x() + other.getHalfLengths()[0];
        double otherFrontLimit = other.center.y() - other.getHalfLengths()[2];
        double otherBackLimit = other.center.z() + other.getHalfLengths()[2];

        double thisUpperLimit = this.center.y() + this.halfHeight;
        double thisLowerLimit = this.center.y() - this.halfHeight;
        double thisLeftLimit = this.center.x() - this.halfWidth;
        double thisRightLimit = this.center.x() + this.halfWidth;
        double thisFrontLimit = this.center.z() - this.halfDepth;
        double thisBackLimit = this.center.z() + this.halfDepth;

        boolean bool = !(otherLeftLimit > thisRightLimit ||
                otherRightLimit < thisLeftLimit ||
                otherLowerLimit > thisUpperLimit ||
                otherUpperLimit < thisLowerLimit ||
                otherFrontLimit > thisBackLimit ||
                otherBackLimit < thisFrontLimit);

        return bool;
    }

    public Point getCenter() { return this.center; }

    public double[] getHalfLengths() { return new double[] {halfWidth, halfHeight, halfDepth}; }
}
