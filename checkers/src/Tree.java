package src;


import java.util.LinkedList;
import java.util.Random;
/*
 * Currently creates a tree with the initial board as the head. Nodes are possible moves that can be made by the Ai
 * Tree does not create moves from the player. Doesn't record full multiJump sequence, just initail jump
 * Doesn't have an evaluator
 * 
 * To Do: 
 * 1) Find way to create children of each node with moves of human player -> started the code for this
 * but may be too big a structure. Keep getting stack overflow errors.
 * 2) Evaluate the children and select on based on score
 * 3) Implement minMax (and Alpha-Beta Pruning)
 */
public class Tree {
    static final int EMPTY = 0, BLACK = 1, RED = 2, BKING = 3, RKING = 4;
    
    private Node head;
    Checker[][] initial;
    int currentPlayer;
    
    
    //New tree with head holding initial board and possible moves as children
    public Tree(LinkedList<Move> mov, Checker[][] board, int player) {
        head = new Node(board,player);
        currentPlayer = player;
        initial = board;
        LinkedList<Move> moves = (LinkedList<Move>) mov.clone();
        System.out.println(moves.size());
       //create all the children
        while(true) {
            head.addChild(moves.pop(), initial);
            if(moves.isEmpty())break;
        }
    }
    
    //returns the xth node in the list
    public Move getChild(int x) {
        return head.children.get(x).getMove();
    }
    
    public Move eval(){
        Move x = head.pickMove();
        return x;
    }

    public class Node{
        int score;
        Move one; //the move made in the node
        Checker[][] board; //copy of board positions
        LinkedList<Node> children; //children of node
        Boolean isMJump; //if the node has possible multijump
        int currentPlayer;
        
        public Node(Checker[][] board, int player) { //creates the head node
            
            this.board = new Checker[8][8];
            this.copyBoard(board);
            printBoard();
            currentPlayer = player;
            children = new LinkedList<Node>();
            
        }
        
        public Node(Move m, Checker[][] board, int player) { //creates a child node
            this.board = new Checker[8][8];
            this.copyBoard(board);
            this.one = m;
            this.currentPlayer = player;
            System.out.println(currentPlayer);
            makeMove(m,this.currentPlayer);
//            children=new LinkedList<Node>();
//            LinkedList<Move> p2 = this.legalMoves(currentPlayer);
////            
//             for (int i = 0; i < p2.size(); i++) {
//                 System.out.println(p2.get(i).toString() +  " " + this.currentPlayer);
//                    }
//            while(true) {
//                    addChild(p2.pop(), getBoard());
//                    if(p2.isEmpty())break;
//                }
        }
        
        //return the move made in node
        public Move getMove() {
            return one;
        }
        
        //make copy of one and place it in this node's board
        public void copyBoard(Checker[][] one) {
            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j<board[i].length; j++) {
                    board[i][j] = one[i][j];
                }
            }
        }
        
        public void setPlayer(int x) {
            this.currentPlayer = x;
        }
        
        //print the board positions
        public void printBoard() {
            System.out.println();
            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j<board[i].length; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.print("\n");
            }
        }
        
        public void addChild(Move m,Checker[][] board) {
            Node x = new Node(m,board, this.currentPlayer);
            children.add(x);
        }
        
        //return the board array
        public Checker[][] getBoard(){
            return this.board;
        }
        
        
        //updates positions with move for evaluation 
        public void makeMove(Move m, int player) {
//            System.out.println(m.from +"  " + m.to);
            int sRow = m.startRow();
            int sCol = m.startCol();
            int eRow = m.endRow();
            int eCol = m.endCol();
//            System.out.println(sRow + " "+sCol + "  " + eRow + " " + eCol);
            Checker temp = new Checker();
            this.board[sRow][sCol] = new Checker(sRow, sCol, 'p', m.from);
            if(m.isJump()) {
                int inRow = m.row(m.inter);
                int inCol = m.column(m.inter);
                    board[inRow][inCol].setColor('p');
                    //check if node could multijump
//                    if(!(this.multiJump(eRow, eCol).isEmpty())) isMJump = false;
                }
            temp.setBoard_pos(m.getTo());
            temp.setX(eRow);
            temp.setY(eCol);
            if(player == RED) {
                temp.setColor('w');
            }else {
                temp.setColor('r');
            }
            board[eRow][eCol] = temp;
            setPlayer(2);
            if(player == 2) {
                setPlayer(1);
            }else {
                setPlayer(2);
            }
        }
        
        //copies of multijump and canJump to run make move
        public LinkedList<Move> multiJump(int row1, int col1) {
            int col = col1;
            int row = row1;
            LinkedList<Move> list = new LinkedList<Move>();
            if(canJump(row, col, row+1, col+1, row+2, col+2)) {
                list.add(new Move(board[row][col].board_pos, board[row+2][col+2].board_pos, board[row+1][col+1].board_pos));
            }
            if(canJump(row, col, row+1, col-1, row+2, col-2)) {
                list.add(new Move(board[row][col].board_pos, board[row+2][col-2].board_pos, board[row+1][col-1].board_pos));
            }
            if(canJump(row, col, row-1, col+1, row-2, col+2)) {
                list.add(new Move(board[row][col].board_pos, board[row-2][col+2].board_pos, board[row-1][col+1].board_pos));
            }
            if(canJump(row, col, row-1, col-1, row-2, col-2)) {
                list.add(new Move(board[row][col].board_pos, board[row-2][col-2].board_pos, board[row-1][col-1].board_pos));
            }
            if(list.isEmpty()) {
                System.out.println("list is empty");
                return null;
            }
            return list;
        }
        
        public boolean canJump(int r1, int c1, int r2, int c2, int r3, int c3) {
            if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8) {
                return false; // jump is off the board
            } else if (board[r3][c3].pieceType != EMPTY) {
                return false; // no place to jump to
            } else if (currentPlayer == RED && (board[r1][c1].pieceType == RED || board[r1][c1].pieceType == RKING)) { 
                // player is red and piece is red
                if ((board[r2][c2].pieceType == BLACK || board[r2][c2].pieceType == BKING)) { // there is a black piece to
                                                                                                // jump
                    if (board[r1][c1].pieceType == RKING) { // King can move forward or back
                        return true;
                    } else {
                        return r1 > r3; // Single must move forward
                    }
                } else {
                    return false; // there is not a black piece to jump
                }
            } else if (currentPlayer == BLACK && (board[r1][c1].pieceType == BLACK || board[r1][c1].pieceType == BKING)) { // player
                                                                                                                            // is
                                                                                                                            // black
                                                                                                                            // and
                                                                                                                            // piece
                                                                                                                            // is
                                                                                                                            // black
                if ((board[r2][c2].pieceType == RED || board[r2][c2].pieceType == RKING)) { // there is a red piece to jump
                    if (board[r1][c1].pieceType == BKING) { // King can move forward or back
                        return true;
                    } else {
                        return r1 < r3; // Single must move forward
                    }
                } else {
                    return false; // there is not a red piece to jump
                }
            }
            return false;
        }
        
        public LinkedList<Move> legalMoves(int player) {
            int king;
            if (player == RED) {
                king = RKING;
            } else {
                king = BKING;
            }
            LinkedList<Move> list = new LinkedList<Move>();
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    if (board[row][col].pieceType == player || board[row][col].pieceType == king) {
                        if (canJump(row, col, row - 1, col - 1, row - 2, col - 2)) {
                            list.add(new Move(board[row][col].board_pos, board[row - 2][col - 2].board_pos,
                                    board[row - 1][col - 1].board_pos));
                        }
                        if (canJump(row, col, row - 1, col + 1, row - 2, col + 2)) {
                            list.add(new Move(board[row][col].board_pos, board[row - 2][col + 2].board_pos,
                                    board[row - 1][col + 1].board_pos));
                        }
                        if (canJump(row, col, row + 1, col - 1, row + 2, col - 2)) {
                            list.add(new Move(board[row][col].board_pos, board[row + 2][col - 2].board_pos,
                                    board[row + 1][col - 1].board_pos));
                        }
                        if (canJump(row, col, row + 1, col + 1, row + 2, col + 2)) {
                            list.add(new Move(board[row][col].board_pos, board[row + 2][col + 2].board_pos,
                                    board[row + 1][col + 1].board_pos));
                        }
                    }
                }
            }

            if (list.isEmpty()) {
                for (int row = 0; row < board.length; row++) {
                    for (int col = 0; col < board[row].length; col++) {
                        if (board[row][col].pieceType == player || board[row][col].pieceType == king) {
                            if (canMove(row, col, row - 1, col - 1, player)) {
                                list.add(new Move(board[row][col].board_pos, board[row - 1][col - 1].board_pos));
                            }
                            if (canMove(row, col, row - 1, col + 1, player)) {
                                list.add(new Move(board[row][col].board_pos, board[row - 1][col + 1].board_pos));
                            }
                            if (canMove(row, col, row + 1, col - 1, player)) {
                                list.add(new Move(board[row][col].board_pos, board[row + 1][col - 1].board_pos));
                            }
                            if (canMove(row, col, row + 1, col + 1, player)) {
                                list.add(new Move(board[row][col].board_pos, board[row + 1][col + 1].board_pos));
                            }
                        }
                    }
                }
            }

                return list;
        }

        public boolean canMove(int fr, int fc, int tr, int tc, int player) {
            if (player != BLACK && player != RED)
                return false; // The player is invalid
            if (tr < 0 || tr >= 8 || tc < 0 || tc >= 8) {
                return false; // jump is off the board
            } else if (board[tr][tc].pieceType != EMPTY) {
                return false; // no place to jump to
            } else if (player == RED && (board[fr][fc].pieceType == RED || board[fr][fc].pieceType == RKING)) { // player is
                                                                                                                // red and
                                                                                                                // piece is
                                                                                                                // red
                if (board[fr][fc].pieceType == RKING && (tr == fr + 1 || tr == fr - 1) && (tc == fc + 1 || tc == fc - 1)) { // King
                                                                                                                            // can
                                                                                                                            // move
                                                                                                                            // forward
                                                                                                                            // or
                                                                                                                            // back
                    return true;
                } else {
                    return (tr == fr - 1 && (tc == fc + 1 || tc == fc - 1)); // Single must move forward
                }
            } else if (currentPlayer == BLACK && (board[fr][fc].pieceType == BLACK || board[fr][fc].pieceType == BKING)) { // player
                                                                                                                            // is
                                                                                                                            // black
                                                                                                                            // and
                                                                                                                            // piece
                                                                                                                            // is
                                                                                                                            // black
                if (board[fr][fc].pieceType == BKING && (tr == fr + 1 || tr == fr - 1) && (tc == fc + 1 || tc == fc - 1)) { // King
                                                                                                                            // can
                                                                                                                            // move
                                                                                                                            // forward
                                                                                                                            // or
                                                                                                                            // back
                    return true;
                } else {
                    return (tr == fr + 1 && (tc == fc + 1 || tc == fc - 1)); // Single must move forward
                }
            } else {
                return false; // there is not a red piece to jump
            }

        }
        

        public Move pickMove(){

            Node best = head.children.get(0);

            Random r = new Random();
            int choose = r.nextInt(head.children.size());
            
            boolean allZ = true;

            //for each node read the middle of the board, if there is a friendly piece
            //add a point to move score. after all moves have been evaluated. Return move
            //with the higehst score
            for(Node n : head.children){
                int points = 0;
                for(int i = 3; i < 6; i++){
                    for(int j = 3; j < 6; j++){
                            if(n.board[i][j].pieceType == currentPlayer){
                                allZ = false;
                                points+=3;
                            }    
                    }
                }
                //prioritize holding the back line
                for(int k = 0; k < 8; k++) {
                    if(currentPlayer == BLACK) {
                        if(n.board[0][k].pieceType == currentPlayer)points+=2;
                        if(n.board[7][k].pieceType == currentPlayer)points++;
                        
                    }else{ 
                        if(n.board[7][k].pieceType == currentPlayer)points+=2;
                        if(n.board[0][k].pieceType == currentPlayer)points++;
                    }
                }
                n.score = points;

                if(n.score > best.score)
                    best = n;

            }


            System.out.println("All Zero " + allZ);
            if(allZ == true){
                return head.children.get(choose).one;
            }

            return best.one;
        }


    }
}
