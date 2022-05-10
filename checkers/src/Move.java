package src;

public class Move {
    int from; // starting position of the checker
    int to; // end position of the checker
    int inter = -1;

    public Move(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public Move(int from, int to, int inter) {
        this.from = from;
        this.to = to;
        this.inter = inter;
    }

    public boolean isJump() { // checks if the move is a jump
        return to - from == 9 || to - from == 7 || from - to == 9 || from - to == 7 || inter != -1;
    }

    // checks if the moves are equal
    public boolean equals(Move other) {
        return other.from == this.from && other.to == this.to;
    }

    public String toString() {
        return "From: " + from + " To position: " + to;
    }

    public int getFrom(){
        return from;
    }

    public int getTo(){
        return to;
    }
    
    public int startRow() {
    	return row(from);
    }
    
    public int endRow() {
    	return row(to);
    }
    
    public int endCol() {
    	return column(to);
    }
    
    public int startCol() {
    	return column(from);
    }
    
    public int interRow() {
    	return row(inter);
    }
    
    public int interCol() {
    	return column(inter);
    }
    
    public int row(int x) {
    	int row = -1;
    	if(x<5) {
    		row = 0;
    	}else if(x > 4 && x < 9) {
    		row = 1;
    	}else if(x > 8 && x < 13) {
    		row = 2;
    	}else if(x > 12 && x < 17) {
    		row = 3;
    	}else if(x > 16 && x < 21) {
    		row = 4;
    	}else if(x > 20 && x < 25) {
    		row = 5;
    	}else if(x > 24 && x < 29) {
    		row = 6;
    	}else if(x> 28) {
    		row =7;
    	}
    	return row;
    }
    
    public int column(int x) {
    	int col = -1;
    	switch(x%8) {
    	case 0:
    		col = 6;
    		break;
    	case 1:
    		col = 1;
    		break;
    	case 2:
    		col = 3;
    		break;
    	case 3:
    		col = 5;
    		break;
    	case 4:
    		col = 7;
    		break;
    	case 5:
    		col = 0;
    		break;
    	case 6:
    		col = 2;
    		break;
    	case 7:
    		col = 4;
    		break;
    	}
    	return col;
    }

	public Object get(int i) {
		// TODO Auto-generated method stub
		return null;
	}

//		
}