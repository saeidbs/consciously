import java.util.ArrayList;

/**
 * Created by saeidbahmani on 12/30/18.
 */



    public class Node {
        private boolean visited;

        private String state;
        private ArrayList<Node> children;
        private Node parent;
        private int cost;
        private int estimatedCostToGoal;
        private int totalCost;
        private int depth;

        public int getDepth() {
            return depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public int getTotalCost() {
            //return totalCost;
            return totalCost-getEstimatedCostToGoal();
        }

        public void setTotalCost(int totalCost) {
            this.totalCost = totalCost;
        }

        public void setTotalCost(int cost, int estimatedCost) {
            this.totalCost = cost + estimatedCost;
            setEstimatedCostToGoal(estimatedCost);
        }

        public int getEstimatedCostToGoal() {
            return estimatedCostToGoal;
        }

        public void setEstimatedCostToGoal(int estimatedCostToGoal) {
            this.estimatedCostToGoal = estimatedCostToGoal;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }



        public Node(String state) {
            this.state = state;
            children = new ArrayList<Node>();
        }


        public String getState() {
            return state;
        }

        public ArrayList<Node> getChildren() {
            return children;
        }


        public void addChild(Node child) {
            children.add(child);
        }
    }