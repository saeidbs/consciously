import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created by saeidbahmani on 12/30/18.
 */
public class NodeHelp {

    public static List<String> getExpand(String state) {
        List<String> expand = new ArrayList<String>();

        switch (state.indexOf("0")) {
            case 0: {
                expand.add(state.replace(state.charAt(0), '$').replace(state.charAt(1), state.charAt(0)).replace('$', state.charAt(1)));
                expand.add(state.replace(state.charAt(0), '$').replace(state.charAt(3), state.charAt(0)).replace('$', state.charAt(3)));
                break;
            }
            case 1: {
                expand.add(state.replace(state.charAt(1), '$').replace(state.charAt(0), state.charAt(1)).replace('$', state.charAt(0)));
                expand.add(state.replace(state.charAt(1), '$').replace(state.charAt(2), state.charAt(1)).replace('$', state.charAt(2)));
                expand.add(state.replace(state.charAt(1), '$').replace(state.charAt(4), state.charAt(1)).replace('$', state.charAt(4)));
                break;
            }
            case 2: {

                expand.add(state.replace(state.charAt(2), '$').replace(state.charAt(1), state.charAt(2)).replace('$', state.charAt(1)));
                expand.add(state.replace(state.charAt(2), '$').replace(state.charAt(5), state.charAt(2)).replace('$', state.charAt(5)));
                break;
            }
            case 3: {
                expand.add(state.replace(state.charAt(3), '$').replace(state.charAt(0), state.charAt(3)).replace('$', state.charAt(0)));
                expand.add(state.replace(state.charAt(3), '$').replace(state.charAt(4), state.charAt(3)).replace('$', state.charAt(4)));
                expand.add(state.replace(state.charAt(3), '$').replace(state.charAt(6), state.charAt(3)).replace('$', state.charAt(6)));
                break;
            }
            case 4: {
                expand.add(state.replace(state.charAt(4), '$').replace(state.charAt(1), state.charAt(4)).replace('$', state.charAt(1)));
                expand.add(state.replace(state.charAt(4), '$').replace(state.charAt(3), state.charAt(4)).replace('$', state.charAt(3)));
                expand.add(state.replace(state.charAt(4), '$').replace(state.charAt(5), state.charAt(4)).replace('$', state.charAt(5)));
                expand.add(state.replace(state.charAt(4), '$').replace(state.charAt(7), state.charAt(4)).replace('$', state.charAt(7)));
                break;
            }
            case 5: {
                expand.add(state.replace(state.charAt(5), '$').replace(state.charAt(2), state.charAt(5)).replace('$', state.charAt(2)));
                expand.add(state.replace(state.charAt(5), '$').replace(state.charAt(4), state.charAt(5)).replace('$', state.charAt(4)));
                expand.add(state.replace(state.charAt(5), '$').replace(state.charAt(8), state.charAt(5)).replace('$', state.charAt(8)));
                break;
            }
            case 6: {
                expand.add(state.replace(state.charAt(6), '$').replace(state.charAt(3), state.charAt(6)).replace('$', state.charAt(3)));
                expand.add(state.replace(state.charAt(6), '$').replace(state.charAt(7), state.charAt(6)).replace('$', state.charAt(7)));
                break;

            }
            case 7: {
                expand.add(state.replace(state.charAt(7), '$').replace(state.charAt(4), state.charAt(7)).replace('$', state.charAt(4)));
                expand.add(state.replace(state.charAt(7), '$').replace(state.charAt(6), state.charAt(7)).replace('$', state.charAt(6)));
                expand.add(state.replace(state.charAt(7), '$').replace(state.charAt(8), state.charAt(7)).replace('$', state.charAt(8)));
                break;
            }
            case 8: {
                expand.add(state.replace(state.charAt(8), '$').replace(state.charAt(5), state.charAt(8)).replace('$', state.charAt(5)));
                expand.add(state.replace(state.charAt(8), '$').replace(state.charAt(7), state.charAt(8)).replace('$', state.charAt(7)));
                break;
            }
        }

        return expand;


    }



    public static void printSolution(Node goalNode, Set<String> visitedNodes, Node root, int poped) {

        int totalCost = 0;

        Stack<Node> solutionStack = new Stack<Node>();
        solutionStack.push(goalNode);
        while (!goalNode.getState().equals(root.getState())) {
            solutionStack.push(goalNode.getParent());
            goalNode = goalNode.getParent();
        }
        String sourceState = root.getState();
        String destinationState;
        int cost = 0;
        for (int i = solutionStack.size() - 1; i >= 0; i--) {
            System.out.println("==============================================");
            destinationState = solutionStack.get(i).getState();
            if (!sourceState.equals(destinationState)) {
                System.out.println("Move " + destinationState.charAt(sourceState.indexOf('0')) + " " + findTransition(sourceState, destinationState));
                //cost = Character.getNumericValue(destinationState.charAt(sourceState.indexOf('0')));
                //cost=0;

                //totalCost += cost;
            }

            sourceState = destinationState;
            System.out.println("Cost of the movement: " + cost);
            System.out.println("$$$$$$$$");
            System.out.println("* " + solutionStack.get(i).getState().substring(0, 3)+" $");
            System.out.println("* " + solutionStack.get(i).getState().substring(3, 6)+" $");
            System.out.println("* " + solutionStack.get(i).getState().substring(6, 9)+" $");
            System.out.println("$$$$$$$$");

        }
        System.out.println("====================================================================");
        System.out.println("** Number of transitions to get to the goal state from the initial state:  " + (solutionStack.size() - 1));
        System.out.println("** Number of visited states:  " + (visitedNodes.size()));
        System.out.println("** Total cost for this solution: " + totalCost);
        System.out.println("** Number of Nodes poped out of the queue: " + poped);
        System.out.println("=====================================================================");

    }

    public static MovementType findTransition(String source, String destination) {
        int zero_position_difference = destination.indexOf('0') - source.indexOf('0');
        switch (zero_position_difference) {
            case -3:
                return MovementType.DOWN;
            case 3:
                return MovementType.UP;
            case 1:
                return MovementType.LEFT;
            case -1:
                return MovementType.RIGHT;
        }
        return null;
    }

    public enum MovementType {
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }


}
