public class Game {
	
	private def running = true;

	private def matrix = [
		[ ' ',' ',' ' ],
		[ ' ',' ',' ' ],
		[ ' ',' ',' ' ]
	];

	private def console( def command ) {
		try {
			new ProcessBuilder( "cmd","/c",command ).inheritIO().start().waitFor();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private def empty() {
		def free = 9;
		
		for ( def i = 0;i < 3;i++ ) {
			for ( def j = 0;j < 3;j++ ) {
				if ( matrix[ i ][ j ] != ' ' ) {
					free--;
				}
			}
		}
		return free;
	}

	private def render() {
		println( matrix[ 0 ][ 0 ] + "|" + matrix[ 0 ][ 1 ] + "|" + matrix[ 0 ][ 2 ] );
		println( "-+-+-" );
		println( matrix[ 1 ][ 0 ] + "|" + matrix[ 1 ][ 1 ] + "|" + matrix[ 1 ][ 2 ] );
		println( "-+-+-" );
		println( matrix[ 2 ][ 0 ] + "|" + matrix[ 2 ][ 1 ] + "|" + matrix[ 2 ][ 2 ] );
		println( "" );
	}

	private userMove() {
		Scanner sc = new Scanner( System.in );
		def x = 0;
		def y = 0;
		
		while ( empty() > 0 ) {
			print( "X > " );
			x = sc.nextInt();
			print( "Y > " );
			y = sc.nextInt();
			x--;
			y--;

			if ( matrix[ x ][ y ] == ' ' ) {
				break;
			} else {
				println( ">INVALID!" );
			}
		}
		matrix[ x ][ y ] = 'X';
	}

	private enemyMove() {
		Random ran = new Random();

		def x = 0;
		def y = 0;
		
		while ( empty() > 0 ) {
			x = ran.nextInt( 3 );
			y = ran.nextInt( 3 );

			if ( matrix[ x ][ y ] == ' ' ) {
				break;
			}
		}
		matrix[ x ][ y ] = 'O';
	}

	private def winner() {
		/*
			00 01 02
			10 11 12
			20 21 22
		*/
		if ( matrix[ 0 ][ 0 ] == matrix[ 0 ][ 1 ] && matrix[ 0 ][ 0 ] == matrix[ 0 ][ 2 ] && matrix[ 0 ][ 0 ] != ' ' )
			return matrix[ 0 ][ 0 ];
		if ( matrix[ 1 ][ 0 ] == matrix[ 1 ][ 1 ] && matrix[ 1 ][ 0 ] == matrix[ 1 ][ 2 ] && matrix[ 1 ][ 0 ] != ' ' )
			return matrix[ 1 ][ 0 ];
		if ( matrix[ 2 ][ 0 ] == matrix[ 2 ][ 1 ] && matrix[ 2 ][ 0 ] == matrix[ 2 ][ 2 ] && matrix[ 2 ][ 0 ] != ' ' )
			return matrix[ 2 ][ 0 ];

		if ( matrix[ 0 ][ 0 ] == matrix[ 1 ][ 0 ] && matrix[ 0 ][ 0 ] == matrix[ 2 ][ 0 ] && matrix[ 0 ][ 0 ] != ' ' )
			return matrix[ 0 ][ 0 ];
		if ( matrix[ 0 ][ 1 ] == matrix[ 1 ][ 1 ] && matrix[ 0 ][ 1 ] == matrix[ 2 ][ 1 ] && matrix[ 0 ][ 1 ] != ' ' )
			return matrix[ 0 ][ 1 ];
		if ( matrix[ 0 ][ 2 ] == matrix[ 1 ][ 2 ] && matrix[ 0 ][ 2 ] == matrix[ 2 ][ 2 ] && matrix[ 0 ][ 2 ] != ' ' )
			return matrix[ 0 ][ 2 ];

		if ( matrix[ 0 ][ 0 ] == matrix[ 1 ][ 1 ] && matrix[ 0 ][ 0 ] == matrix[ 2 ][ 2 ] && matrix[ 0 ][ 0 ] != ' ' )
			return matrix[ 0 ][ 0 ];
		if ( matrix[ 0 ][ 2 ] == matrix[ 1 ][ 1 ] && matrix[ 0 ][ 2 ] == matrix[ 2 ][ 0 ] && matrix[ 0 ][ 2 ] != ' ' )
			return matrix[ 0 ][ 2 ];

		return 'D';
	}

	public Game() {
		while ( running ) {
			console( "cls" );
			println( "EMPTY : " + empty() );
			render();
			if ( running ) userMove();
			if ( running ) enemyMove();
			if ( winner() == 'D' && empty() == 0 ) {
				println();
				render();
				println( "===NO WINNER===" );
				running = false;
			} else if ( winner() == 'X' ) {
				println();
				render();
				println( "===X WINS===" );
				running = false;
			} else if ( winner() == 'O' ) {
				println();
				render();
				println( "===O WINS===" );
				running = false;
			}
		}
	}
}
