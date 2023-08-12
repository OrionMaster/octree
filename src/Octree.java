import PointsStack.Point;
import PointsStack.PointStack;

public class Octree {

    //max amount of points in one Octree
    public final int OT_CAPACITY = 4;

    //boundaries of Octree node
    Cuboid boundary;

    //point storage with all points this Octree
    PointStack points = new PointStack();

    //all children nodes of current Octree node
    Octree[] children = new Octree[] {null, null, null, null, null, null, null, null};

    public Octree(Cuboid boundary) {
        this.boundary = boundary;
    }

    /** ------------
        OCTREE CREATION PART
     ------------**/
    // insert given point into the Octree
    public boolean insert(Point p){

        // if point is not inside this node return false
        if(!boundary.contains(p)) return false;

        // if this node has capacity more points and is not subdivided,
        // push the point to this node points stack and return true
        if(points.size() < OT_CAPACITY && children[0] == null) {
            points.push(p);
            return true;
        }

        // if this node is not subdivide, subdivide it and insert all points of this node into the new children.
        if(children[0] == null) {
            subdivide();
            Point[] ps = points.getAll();
            for(Point sP : ps) {
                this.insert(sP);
            }
        }

        // insert point into all subtrees. one of them will store it, everyone else will return false
        for(Octree child : children) {
            if(child.insert(p)) return true;
        }

        //should never happen
        return false;
    }

    // this method will subdivide the current node into 8 sub-nodes.
    // imagine these this node is a cube and each sub-node is a corner of this cube.
    public void subdivide() {
        double x = this.boundary.getCenter().x();
        double y = this.boundary.getCenter().y();
        double z = this.boundary.getCenter().z();
        double halfWidth = this.boundary.getHalfLengths()[0]/2;
        double halfHeight = this.boundary.getHalfLengths()[1]/2;
        double halfDepth = this.boundary.getHalfLengths()[2]/2;

        int secCount = 0;
        int thridCount = 0;
        for(int i = 0; i < 8; i++) {
            double cX = i%2 == 0 ? x - halfWidth : x + halfWidth;
            double cY = secCount < 2 ? y + halfHeight : y - halfHeight;
            double cZ = thridCount < 4 ? z - halfDepth : z + halfDepth;
            secCount++;
            thridCount++;
            if (secCount == 4) secCount = 0;
            if (thridCount == 8) thridCount = 0;

            Cuboid boundary = new Cuboid(new Point(cX, cY, cZ), halfWidth, halfHeight, halfDepth );
            children[i] = new Octree(boundary);
        }
    }

    /** -------------
       OCTREE SEARCH PART
     ---------------**/
    //searches for points in specific range
    public PointStack queryRange(Cuboid range) {
        // stores all points of node inside the range boundaries.
        PointStack pointsInRange = new PointStack();

        // if the current node boundaries doesn't intersect the range boundaries return PointStack with head = null
        if(!this.boundary.intersects(range)) return pointsInRange;

        // if the current node isn't subdivided we are at an end of the tree.
        // we collect all points of this node and check every point for its position inside the range boundaries.
        if(this.children[0] == null) {
            Point[] pointsArray = this.points.getAll();
            if(pointsArray != null) {
                for(Point p: pointsArray) {
                    if(range.contains(p)) pointsInRange.push(p);
                }
            }
            return pointsInRange;
        }

        // add all points of the sub-nodes to points of this node
        for(Octree child : children) {
            pointsInRange.pushStack(child.queryRange(range));
        }
        return pointsInRange;
    }
}
