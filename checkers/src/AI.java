package src;

import java.util.LinkedList;
import java.util.Random;

public class AI{

    int color;
    Game current;
//    Checker[] board;
//    LinkedList<Move> moves;

    AI(int c){
        color = c;
    }
    AI(Game playing){
    	current = playing;
    	color = playing.AI_player;
    }

    public Move doSomething(LinkedList<Move> m, Checker[][] board){
    	Random r = new Random();
        int choose = r.nextInt(m.size());
//        Move x = m.get(choose);
        Tree moveTree = new Tree(m,board,color);
//    	return moves.evaluate();
        System.out.println(choose);
    	Move x = moveTree.getChild(choose);
        
//        
//        //print AI moves
//        // for (int i = 0; i < m.size(); i++) {
//		// 	System.out.println(m.get(i).toString());
//        // }
        
        return x;
    }

    public Move evaluatedMove(LinkedList<Move> m, Checker[][] board){
        Tree moveTree = new Tree(m,board,color);
        Move x = moveTree.eval();

        return x;
    }


}