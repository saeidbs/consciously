import java.util.*;

/**
 * Created by saeidbahmani on 12/30/18.
 */
public class Search {
    Set<String> bfsStateSets;
    Set<String> dfsStateSets;
    private Node root;
    private Node likeRoot;
    private String goalState;
    private String likeGoalState;

    public Node getRoot(){

        return root;
    }

    public void setRoot(Node root){
        this.root=root;

    }

    public String getGoalSate() {
        return goalState;
    }

    public void setGoalSate(String goalSate) {
        this.goalState = goalSate;
    }

    public Search(Node root, String goalSate) {
        this.root = root;
        likeRoot=root;
        this.goalState = goalSate;
        likeGoalState=goalSate;
    }


    public void bfs(){

        bfsStateSets = new HashSet<String>();


        // Node node = new Node(root.getState());
        Node node = new Node(likeRoot.getState());
        Queue<Node> queue = new LinkedList<Node>();
        int polls=0;
        Node currentNode=node;
        goalState=likeGoalState;
        while (!currentNode.getState().equals(goalState)){
            //  System.out.println("bfswow");
            bfsStateSets.add(currentNode.getState());

            List<String>nodeExpand=NodeHelp.getExpand(currentNode.getState());

            for(String string:nodeExpand){

                if(bfsStateSets.contains(string))
                    continue;

                bfsStateSets.add(string);
                Node child=new Node(string);
                currentNode.addChild(child);
                child.setParent(currentNode);
                queue.add(child);
            }
            currentNode=queue.poll();
            polls++;
        }
        NodeHelp.printSolution(currentNode, bfsStateSets, root, polls);

        System.out.println(currentNode.getState());

    }


    public void dfs(){

        dfsStateSets = new HashSet<String>();


        Node node = new Node(root.getState());
        Stack<Node> stack = new Stack<Node>();
        Node currentNode=node;
        Stack<Node> sampleStack=new Stack<>();
        int poped=0;

        while (!currentNode.getState().equals(goalState)){
            //   System.out.println("dfswow");
            dfsStateSets.add(currentNode.getState());

            List<String>nodeExpand=NodeHelp.getExpand(currentNode.getState());

            for(String string:nodeExpand){

                if(dfsStateSets.contains(string))
                    continue;

                dfsStateSets.add(string);
                Node child=new Node(string);
                currentNode.addChild(child);
                child.setParent(currentNode);

                //  stack.add(child);
                //test for real dfs
                sampleStack.add(child);
            }
            while (!sampleStack.isEmpty()){

                stack.add(sampleStack.pop());

            }
            currentNode=stack.pop();
            poped++;

        }

         NodeHelp.printSolution(currentNode, dfsStateSets, root, poped);
        System.out.println(currentNode.getState());


    }
    public void ucs() {
        Set<String> stateSets = new HashSet<String>();
        //int totalCost = 0;
        int polls = 0;
        Node node = new Node(root.getState());
        node.setCost(0);

        NodeComparator nodePriorityComparator = new NodeComparator();

        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(5, nodePriorityComparator);
        Node currentNode = node;
        while (!currentNode.getState().equals(goalState)) {
            stateSets.add(currentNode.getState());
            List<String> nodeExpand = NodeHelp.getExpand(currentNode.getState());
            for (String string : nodeExpand) {
                if (stateSets.contains(string))
                    continue;
                stateSets.add(string);
                Node child = new Node(string);
                currentNode.addChild(child);
                child.setParent(currentNode);
                // child.setTotalCost(currentNode.getTotalCost() + Character.getNumericValue(child.getState().charAt(child.getParent().getState().indexOf('0'))), 0);
                child.setTotalCost(currentNode.getTotalCost()+getUcsCost(child));

                nodePriorityQueue.add(child);

            }
            currentNode = nodePriorityQueue.poll();
            polls++;
        }
        NodeHelp.printSolution(currentNode, stateSets, root, polls);

    }

    public void aStar(){

        Set<String> stateSets = new HashSet<String>();
        //int totalCost = 0;
        int polls = 0;
        Node node = new Node(root.getState());
        node.setTotalCost(0);


        NodeComparator nodePriorityComparator = new NodeComparator();


        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(5, nodePriorityComparator);
        Node currentNode = node;

        while (!currentNode.getState().equals(goalState)) {

            stateSets.add(currentNode.getState());
            List<String> nodeExpand = NodeHelp.getExpand(currentNode.getState());
            for (String string : nodeExpand) {
                if (stateSets.contains(string))
                    continue;
                stateSets.add(string);
                Node child = new Node(string);

                currentNode.addChild(child);
                child.setParent(currentNode);


                child.setTotalCost(currentNode.getTotalCost() + getUcsCost(child), heuristicTwo(child.getState(), goalState));
                //System.out.println( heuristicTwo(currentNode.getState(), goalState));
                nodePriorityQueue.add(child);

            }
            currentNode = nodePriorityQueue.poll();
            polls++;
        }
        NodeHelp.printSolution(currentNode, stateSets, root, polls);
    }

    public void rbfs(){

        Set<String> stateSets = new HashSet<String>();
        //int totalCost = 0;
        int polls = 0;
        Node node = new Node(root.getState());
        node.setTotalCost(0);


        NodeComparator nodePriorityComparator = new NodeComparator();


        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(5, nodePriorityComparator);
        Node currentNode = node;

        while (!currentNode.getState().equals(goalState)) {

            List<String> nodeExpand = NodeHelp.getExpand(currentNode.getState());
            boolean boolRbfs=false;
            int min=100000;
            for (String string:nodeExpand){
                Node child =new Node(string);
                min=Math.min(min,heuristicOne(child.getState(),goalState));
                //if(currentNode.getTotalCost()+getUcsCost(child)<heuristicOne(child.getState(),goalState))
             //   if(heuristicOne(currentNode.getState(),goalState)>=heuristicOne(child.getState(),goalState))
                if(currentNode.getEstimatedCostToGoal()>=heuristicOne(child.getState(),goalState))
                    boolRbfs=true;

            }
            if(!boolRbfs){
                if(currentNode.getTotalCost()==0){
                    currentNode.setTotalCost(0,min);
                }
                else {
                    //currentNode.setTotalCost(currentNode.getTotalCost() - heuristicOne(currentNode.getState(), goalState), min);
                    currentNode.setTotalCost(currentNode.getTotalCost() - currentNode.getEstimatedCostToGoal(), min);
                }
                    nodePriorityQueue.add(currentNode);

            }
            else {

                stateSets.add(currentNode.getState());
                for (String string : nodeExpand) {
                    if (stateSets.contains(string))
                        continue;
                    stateSets.add(string);
                    Node child = new Node(string);

                    currentNode.addChild(child);
                    child.setParent(currentNode);


                    child.setTotalCost(currentNode.getTotalCost() + getUcsCost(child), heuristicOne(child.getState(), goalState));

                    //changing from astar for the truth path
                    System.out.println(child.getEstimatedCostToGoal());

//                if(child.getParent()!=null)
//                    if (child.getParent().getParent()!=null)
//                         if (child.getEstimatedCostToGoal()>child.getParent().getEstimatedCostToGoal())
//                              child.setParent(child.getParent().getParent());


                    nodePriorityQueue.add(child);

                }
            }
            currentNode = nodePriorityQueue.poll();
            polls++;
        }
        NodeHelp.printSolution(currentNode, stateSets, root, polls);
    }



    public void bds(){


        // head of properties for bfs
        bfsStateSets = new HashSet<String>();
        // Node node = new Node(root.getState());
        likeRoot=new Node(goalState);
        likeGoalState=root.getState();
        Node bfsNode = new Node(likeRoot.getState());
        Queue<Node> queue = new LinkedList<Node>();

        Node bfsCurrentNode=bfsNode;
        String bfsGoalState=likeGoalState;
        int polls=0;
        //end properites for bfs

        //head of properties for dfs
        dfsStateSets = new HashSet<String>();


        Node dfsNode = new Node(root.getState());
        Stack<Node> dfsStack = new Stack<Node>();
        Node dfsCurrentNode=dfsNode;
        Stack<Node> dfsSampleStack=new Stack<>();
        int poped=0;

        //end properties for dfs

        boolean whileBollean=true;
       // while (!bfsCurrentNode.getState().equals(goalState)){
        while (whileBollean){
            //  System.out.println("bfswow");
            bfsStateSets.add(bfsCurrentNode.getState());
            dfsStateSets.add(dfsCurrentNode.getState());
            //check for end the while
            whileBollean=whileBds();

            List<String>bfsNodeExpand=NodeHelp.getExpand(bfsCurrentNode.getState());
            List<String>dfsNodeExpand=NodeHelp.getExpand(dfsCurrentNode.getState());

            //bfs node expand and child
            for(String string:bfsNodeExpand){

                if(bfsStateSets.contains(string))
                    continue;

                bfsStateSets.add(string);

                whileBollean=whileBds();

                Node child=new Node(string);
                bfsCurrentNode.addChild(child);
                child.setParent(bfsCurrentNode);
                queue.add(child);
            }
            bfsCurrentNode=queue.poll();
            polls++;
            //end bfs expand
            //dfs expand
            for(String string:dfsNodeExpand){

                if(dfsStateSets.contains(string))
                    continue;

                dfsStateSets.add(string);
                whileBollean=whileBds();
                Node child=new Node(string);
                dfsCurrentNode.addChild(child);
                child.setParent(dfsCurrentNode);

                //  stack.add(child);
                //test for real dfs
                dfsSampleStack.add(child);
            }
            while (!dfsSampleStack.isEmpty()){

                dfsStack.add(dfsSampleStack.pop());
            }
            dfsCurrentNode=dfsStack.pop();
            poped++;
            //dfs end

        }




        //NodeHelp.printSolution(bfsCurrentNode, bfsStateSets, root, polls);

       // System.out.println(bfsCurrentNode.getState());


    }


    public void testBds(){

        Thread dfsThread= new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("olala");

                dfs();

            }
        });
        Thread bfsThread =new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("lila");
                likeRoot=new Node(goalState);
                likeGoalState=root.getState();
                bfs();

            }
        });

        dfsThread.start();
        bfsThread.start();
        Boolean whileBool=true;
        while (whileBool){
            System.out.println("uhum");
            if (dfsStateSets!=null && !dfsStateSets.isEmpty()) {
                System.out.println(bfsStateSets);
                System.out.println(dfsStateSets);

                Object[] array=dfsStateSets.toArray();
                for (int i = 0; i < array.length; i++) {

                    System.out.println("njjnrnvr");
                    if (bfsStateSets.contains(array[i])) {

                        System.out.println("dsfdsdsddsdsdsdssddsssdsda");
                        dfsThread.stop();
                        bfsThread.stop();
                        whileBool = false;
                        break;

                    }
                }
            }
        }



    }



    public boolean whileBds(){
        if (dfsStateSets!=null && !dfsStateSets.isEmpty()) {
          //  System.out.println(bfsStateSets);
            //System.out.println(dfsStateSets);

            Object[] array=dfsStateSets.toArray();
            for (int i = 0; i < array.length; i++) {

              //  System.out.println("njjnrnvr");
                if (bfsStateSets.contains(array[i])) {

                    System.out.println("yeahBaby");
                    System.out.println(array[i]);

                    return false;


                }
            }

        }
        return true;

    }

    public int getUcsCost(Node node){
        Node parent= node.getParent();

        //int parentStayZero=parent.getState().indexOf("0");
        int childStayZero=node.getState().indexOf("0");
        int whichChanged= parent.getState().charAt(childStayZero);
        int childStayChanged=node.getState().indexOf(whichChanged);
        int cost=whichChanged-(childStayChanged+1);
        return Math.abs(cost);


    }

    private int heuristicTwo(String currentState, String goalSate) {
        int difference = 0;
        for (int i = 0; i < currentState.length(); i++ )
            for (int j = 0; j < goalSate.length(); j++)
                if (currentState.charAt(i) == goalSate.charAt(j))
                    difference = difference + ((Math.abs(i % 3 - j % 3)) + Math.abs(i / 3 + j / 3));
        return difference;
    }





    private int heuristicThree(String currentState, String goalSate) {
        int difference = 0;
        int manhattanDistance = 0;
        for (int i = 0; i < currentState.length(); i += 1)
            for (int j = 0; j < goalSate.length(); j += 1)
                if (currentState.charAt(i) == goalSate.charAt(j))
                    manhattanDistance = (Math.abs(i % 3 - j % 3)) + Math.abs(i / 3 + j / 3);
        difference = difference + 2 * manhattanDistance - 1;
        return difference;
    }



    private int heuristicOne(String currentState, String goalSate) {
        int difference = 0;
        for (int i = 0; i < currentState.length(); i += 1)
            if (currentState.charAt(i) != goalSate.charAt(i))
                difference += 1;
        return difference;
    }






}