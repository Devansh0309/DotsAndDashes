import java.io.*;
import java.util.*;
class DotsAndDashes{
	static int NumberOfSquares;
	static int[] horizontal;static int[] vertical; // horizontal array for storing 1: means "_" is present between consecutive dots, 0-vice versa and similarly for vertical: "|"
	static String dotsAndDashes[][];
	public static void main(String args[])throws IOException{
		Scanner sc = new Scanner(System.in);
		int m=3;int n=4;
		dotsAndDashes=new String[4*m-3][2*n-1];
		for(int i=0;i<4*m-3;i++) {
			for(int j=0;j<2*n-1;j++) {
				if(i%4==0 && j%2==0) {
					dotsAndDashes[i][j]="*";
				}
				else if(i%4==0&& j%2!=0) {
					dotsAndDashes[i][j]="        ";
				}
				else if(i%4!=0 && j%2==0) {
					dotsAndDashes[i][j]=" ";
				}
				else {
					dotsAndDashes[i][j]="        ";
				}
				
			}
		}
		print(dotsAndDashes,m,n);
		System.out.println();
		System.out.println();
		//reset not available currently, undo by 1 step, available only after input of at least one dot
		NumberOfSquares=0;
		horizontal=new int[m*n-m];
		vertical=new int[m*n-n];
		int count=0;int p=-1;int q=-1;
		int x11;int y11;int x12;int y12;int x21;int y21;int x22;int y22;
		int P1score=0; int P2score=0;
		while(NumberOfSquares<(m-1)*(n-1)) {
			boolean firstTime=true;
			while(NumberOfSquares<(m-1)*(n-1) && (firstTime==true || count>0)) {
				int pos1[][]=ValidInput("Player1",m,n,sc);
			    x11=pos1[0][0];y11=pos1[0][1];x12=pos1[1][0];y12=pos1[1][1]; //xij means xj of (xj,yj) where i is player number and j is dot/point/asterisk/star number
			    //Update Horizontal and Vertical with 0, 1 for Player 1
			    int i=indexOfDot(pos1);
			    if(i==0) { //2nd dot is right of 1st and x11==x12
			    	horizontal[x11*(n-1)+y11]=1;
				    p=x11;q=y11;
				    dotsAndDashes[4*p][2*q+1]="________";
				    }
			    else if(i==1) { //2nd dot is left of 1st and x11==x12
			    	horizontal[x11*(n-1)+y12]=1;
				    p=x11;q=y12;
				    dotsAndDashes[4*p][2*q+1]="________";
				    }
			    else if(i==2) { //2nd dot is down of 1st and y11==y12
				   vertical[y11*(m-1)+x11]=1;
				   p=x11;q=y11;
				   dotsAndDashes[4*p+2][2*q]="|";
				   dotsAndDashes[4*p+3][2*q]="|";
			       }
			    else { //2nd dot is up of 1st and y11==y12
				   vertical[y11*(m-1)+x12]=1;
				   p=x12;q=y11;
				   dotsAndDashes[4*p+2][2*q]="|";
				   dotsAndDashes[4*p+3][2*q]="|";
			       }
			    count=SquareFormed(i,p,q,m,n,"P1");
			    NumberOfSquares+=count;
			    firstTime=false;
			    P1score=P1score+count;
			    print(dotsAndDashes,m,n);
			    System.out.println();
			    if(count>0) {
			    	System.out.println(" P1 Score: "+P1score+" P2 Score: "+P2score);
			    }
				System.out.println();
		}
			
			firstTime=true;
			
			while(NumberOfSquares<(m-1)*(n-1) && (count>0 || firstTime==true)) {
				int pos2[][]=ValidInput("Player2",m,n,sc);
			    x21=pos2[0][0];y21=pos2[0][1];x22=pos2[1][0];y22=pos2[1][1]; //xij means xj of (xj,yj) where i is player number and j is dot/point/asterisk/star number
			    //Update Horizontal and Vertical with 0, 1 for Player 2
			    int index=indexOfDot(pos2);
		        if(index==0) { //2nd dot is right of 1st and x21==x22
		        	horizontal[x21*(n-1)+y21]=1;
		    	    p=x21;q=y21;
		    	    dotsAndDashes[4*p][2*q+1]="________";
		    	    }
		        else if(index==1) { //2nd dot is left of 1st and x21==x22
		        	horizontal[x21*(n-1)+y22]=1;
			        p=x21;q=y22;
			        dotsAndDashes[4*p][2*q+1]="________";
				    }
		        else if(index==2) { //2nd dot is down of 1st and y21==y22
				    vertical[y21*(m-1)+x21]=1;
				    p=x21;q=y21;
					dotsAndDashes[4*p+2][2*q]="|";
					dotsAndDashes[4*p+3][2*q]="|";
				    }
		        else { //2nd dot is up of 1st and y21==y22
				    vertical[y21*(m-1)+x22]=1;
				    p=x22;q=y21;
					dotsAndDashes[4*p+2][2*q]="|";
					dotsAndDashes[4*p+3][2*q]="|";
				    }
		            count=SquareFormed(index,p,q,m,n,"P2");
			        NumberOfSquares+=count;
			        firstTime=false;
			        P2score=P2score+count;
			        print(dotsAndDashes,m,n);
			        System.out.println();
			        if(count>0) {
				    	System.out.println(" P1 Score: "+P1score+" P2 Score: "+P2score);
				    }
					System.out.println();
			        }
			
		}
		if(P1score>P2score) {
			System.out.println("Player1 wins");
		}
		else if(P2score>P1score) {
			System.out.println("Player2 wins");
		}
		else {
			System.out.println("Tie");
		}
	}
	public static int indexOfDot(int pos[][]) {
		int dirs[][]= {{0,1},{0,-1},{1,0},{-1,0}}; //{Right,Left,Down,Up}
		int p=-1;int q=-1;int i;
		for(i=0;i<=3;i++)
		{
			p=dirs[i][0]+pos[0][0]; 
			q=dirs[i][1]+pos[0][1]; 
			if(p==pos[1][0] && q==pos[1][1]) { 
				break;
			}
			
		}
		return i;
	}
	public static int SquareFormed(int i,int p,int q,int m,int n,String s) {
		if(i==0 || i==1) {
			if(p==0) {
				if((horizontal[q]&horizontal[n-1+q]&vertical[q*(m-1)]&vertical[(q+1)*(m-1)])==1) {
					dotsAndDashes[2][2*q+1]="   "+s+"   ";
					return 1;
				}
			}
			else if(p==m-1) {
				if((horizontal[(m-1)*(n-1)+q]&horizontal[(m-2)*(n-1)+q]&vertical[q*(m-1)+m-2]&vertical[(q+1)*(m-1)+m-2])==1) {
					dotsAndDashes[4*m-6][2*q+1]="   "+s+"   ";
					return 1;
				}
			}
			else {
				if((horizontal[p*(n-1)+q]&horizontal[(p+1)*(n-1)+q]&vertical[q*(m-1)+p]&vertical[(q+1)*(m-1)+p])==1 && (horizontal[p*(n-1)+q]&horizontal[(p-1)*(n-1)+q]&vertical[q*(m-1)+p-1]&vertical[(q+1)*(m-1)+p-1])==0) {
					dotsAndDashes[4*p+2][2*q+1]="   "+s+"   ";
					return 1;
				}
				else if((horizontal[p*(n-1)+q]&horizontal[(p+1)*(n-1)+q]&vertical[q*(m-1)+p]&vertical[(q+1)*(m-1)+p])==0 && (horizontal[p*(n-1)+q]&horizontal[(p-1)*(n-1)+q]&vertical[q*(m-1)+p-1]&vertical[(q+1)*(m-1)+p-1])==1) {
					dotsAndDashes[4*p-2][2*q+1]="   "+s+"   ";
					return 1;
				}
				else if((horizontal[p*(n-1)+q]&horizontal[(p+1)*(n-1)+q]&vertical[q*(m-1)+p]&vertical[(q+1)*(m-1)+p])==1 && (horizontal[p*(n-1)+q]&horizontal[(p-1)*(n-1)+q]&vertical[q*(m-1)+p-1]&vertical[(q+1)*(m-1)+p-1])==1){
					dotsAndDashes[4*p+2][2*q+1]="   "+s+"   ";
					dotsAndDashes[4*p-2][2*q+1]="   "+s+"   ";
					return 2;
				}
			}
		}
		else {
			if(q==0) {
				if((horizontal[p*(n-1)]&horizontal[(p+1)*(n-1)]&vertical[p]&vertical[(m-1)+p])==1) {
					dotsAndDashes[4*p+2][1]="   "+s+"   ";
					return 1;
				}
			}
			else if(q==n-1) {
				if((horizontal[p*(n-1)+n-2]&horizontal[(p+1)*(n-1)+n-2]&vertical[(n-1)*(m-1)+p]&vertical[(n-2)*(m-1)+p])==1) {
					dotsAndDashes[4*p+2][2*n-3]="   "+s+"   ";
					return 1;
				}
			}
			else {
				if((horizontal[p*(n-1)+q]&horizontal[(p+1)*(n-1)+q]&vertical[q*(m-1)+p]&vertical[(q+1)*(m-1)+p])==1 && (horizontal[p*(n-1)+q-1]&horizontal[(p+1)*(n-1)+q-1]&vertical[q*(m-1)+p]&vertical[(q-1)*(m-1)+p])==0) {
					dotsAndDashes[4*p+2][2*q+1]="   "+s+"   ";
					return 1;
				}
				else if((horizontal[p*(n-1)+q]&horizontal[(p+1)*(n-1)+q]&vertical[q*(m-1)+p]&vertical[(q+1)*(m-1)+p])==0 && (horizontal[p*(n-1)+q-1]&horizontal[(p+1)*(n-1)+q-1]&vertical[q*(m-1)+p]&vertical[(q-1)*(m-1)+p])==1) {
					dotsAndDashes[4*p+2][2*q-1]="   "+s+"   ";
					return 1;
				}
				else if((horizontal[p*(n-1)+q]&horizontal[(p+1)*(n-1)+q]&vertical[q*(m-1)+p]&vertical[(q+1)*(m-1)+p])==1 && (horizontal[p*(n-1)+q-1]&horizontal[(p+1)*(n-1)+q-1]&vertical[q*(m-1)+p]&vertical[(q-1)*(m-1)+p])==1){
					dotsAndDashes[4*p+2][2*q+1]="   "+s+"   ";
					dotsAndDashes[4*p+2][2*q-1]="   "+s+"   ";
					return 2;
				}
			}
		}
		return 0;
	}
	public static int countDigits(int num) {
		if(num==0) {
			return 1;
		}
		int count=0;
		while(num!=0) {
			num=num/10;
			count++;
		}
		return count;
	}
	public static void print(String arr[][],int m,int n) {
		System.out.print("   ");
		for(int i=0;i<n;i++) {
			System.out.print(i);
			int dig=countDigits(i);
			for(int j=1;j<=9-dig;j++) {
				System.out.print(" ");
			}
		}
		System.out.println();
		System.out.println();
		System.out.println();
		for(int i=0;i<4*m-3;i++) {
			System.out.print("   ");
			for(int j=0;j<2*n-1;j++) {
				System.out.print(arr[i][j]);
			}
			if(i%4==0) {
				System.out.print("   "+i/4);
			}
			else {
			System.out.println();
			}
		}
	}
	public static int[][] ValidInput(String s,int m,int n,Scanner sc) {
		int pos[][]=new int[2][2];boolean check=false;String t="";
		while(check==false) {
		System.out.println(s+" "+"enter position of dot 1 from where you want to start line, enter x or row no. followed by space then y or col. no.  of dot(x,y). Press enter after that");
		while(true) {
			//String s[]=sc.nextLine().split(" ")
			//validate integer input of x,y
			while(!sc.hasNextInt()) {
				sc.nextLine();
				System.out.println("Renter row no/x of first dot, enter integer values only!");
			}
			pos[0][0]=sc.nextInt();
			while(!sc.hasNextInt()) {
				System.out.println("Renter col. no/y of first dot, enter integer values only!");
				sc.nextLine();
			}
			pos[0][1]=sc.nextInt();
			sc.nextLine();
			System.out.println();
			int p=pos[0][0];
			int q=pos[0][1];
			int j;
			if(p>-1 && p<m && q>-1 && q<n ) { //for checking if address of dot is valid or not?
				for(j=0;j<1;j++) {
					if(p>-1 && p<m && q+1>-1 && q+1<n && horizontal[p*(n-1)+q]==0) { //Right
						break;
					}
					if(p>-1 && p<m && q-1>-1 && q-1<n && horizontal[p*(n-1)+q-1]==0) { //Left
						break;
					}
					if(p-1>-1 && p-1<m && q>-1 && q<n && vertical[q*(m-1)+p-1]==0) { //Up
						break;
					}
					if(p+1>-1 && p+1<m && q>-1 && q<n && vertical[q*(m-1)+p]==0) { //Down
						break;
					}
				}
				System.out.println("Do you want to undo press U else type anything to move forward");
				t=sc.next();
				if(j==0 && !t.equals("U")) {
					break;
				}
				else if(j==0 && t.equals("U")) {
					System.out.println(s+" "+"enter position of dot 1 from where you want to start line, enter x or row no. followed by space then y or col. no.  of dot(x,y). Press enter after that");
				}
				else if(j==1) {
				System.out.println("Line already present from this dot to its atmost 2 neighbours, renter another dot position");
				}
			}
			else {
			System.out.println("!Invalid position of 1st dot, renter correct values");
			}
		}
		System.out.println("Enter position of next valid consecutive dot then press enter");
		while(true) {
			//validate integer input of x,y
			int count=0;
			while(!sc.hasNextInt()) {
				count++;
				System.out.println("Renter row no/x of second dot, enter integer values only!"+count);
				sc.nextLine();
			}
			pos[1][0]=sc.nextInt();
			while(!sc.hasNextInt()) {
				System.out.println("Renter col. no/y of second dot, enter integer values only!");
				sc.nextLine();
			}
			pos[1][1]=sc.nextInt();
			if(pos[1][0]>-1 && pos[1][0]<m && pos[1][1]>-1 && pos[1][1]<n && (pos[0][0]!=pos[1][0] || pos[0][1]!=pos[1][1])) {
				int i=indexOfDot(pos); //this step and next steps for checking if line is already present between dots?
				if(i==0) { //2nd dot is right of 1st and x21==x22
			    	if(horizontal[pos[0][0]*(n-1)+pos[0][1]]==0) {check=true;break;}
				}
			    else if(i==1) { //2nd dot is left of 1st and x21==x22
				    if(horizontal[pos[0][0]*(n-1)+pos[1][1]]==0) {check=true;break;}
			    }
			    else if(i==2) { //2nd dot is down of 1st and y21==y22
					if(vertical[pos[0][1]*(m-1)+pos[0][0]]==0){check=true;break;}
					}
			    else if(i==3){ //2nd dot is up of 1st and y21==y22
					if(vertical[pos[0][1]*(m-1)+pos[1][0]]==0){check=true;break;}
					}
			    else if(i==4){
			    	System.out.println("!!Invalid position of 2nd dot, renter correct values");
			    }
			    else {
				System.out.println("!Line already present,renter another position of 2nd dot");
			    }
			}
			else {
			System.out.println("!Invalid position of 2nd dot, renter correct values");
			}
		}
		System.out.println("Do you want to undo press U else type anything to move forward");
		t=sc.next();
		if(t.equals("U")) {
			check=false;
		}
		/*if(sc.next()=="R") { //Reset logic
			check=false;
			for(int k=0;k<4*m-3;k++) {
				for(int j=0;j<2*n-1;j++) {
					if(k%4==0 && j%2==0) {
						dotsAndDashes[k][j]="*";
					}
					else if(k%4==0 && j%2!=0) {
						dotsAndDashes[k][j]="        ";
					}
					else if(k%4!=0 && j%2==0) {
						dotsAndDashes[k][j]=" ";
					}
					else {
						dotsAndDashes[k][j]="        ";
					}
					
				}
			}
			for(int k=0;k<m*n-m;k++) {
				horizontal[k]=0;
			}
			for(int k=0;k<m*n-n;k++) {
				vertical[k]=0;
			}
			print(dotsAndDashes,m,n);
		}*/
		}
		
		return pos;	
	}
}