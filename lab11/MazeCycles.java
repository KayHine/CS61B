import java.util.Observable;
import java.util.Stack;
/** 
 *  @author Josh Hug
 */

public class MazeCycles extends MazeExplorer {
    /* Inherits public fields: 
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private int s;
    private Stack<Integer> cycle;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        // set some arbitrary source (1, 1)
        s = maze.xyTo1D(1, 1);
        edgeTo[s] = s;
    }

    private void dfscycles(int parent, int v) {
        marked[v] = true;
        announce();

        for (int w : maze.adj(v)) {

            // short circuit if cycle already found
            if (cycle != null) {
                return;
            }

            if (!marked[w]) {
                edgeTo[w] = v;
                announce();
                dfscycles(v, w);
                // check for cycle (but disregard reverse of edge leading to v)
            } else if (w != parent) {
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }
    }

    @Override
    public void solve() {
        dfscycles(-1, s);
    }
} 

