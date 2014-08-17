package revertHashAtRange;

import java.security.MessageDigest;
import java.math.BigDecimal;
import java.math.BigInteger;

import static convertToSequence.convertToSequence.convertToSequence;

public class revertHashAtRange{
	private static final String EMPTY_STRING = "";
	private static final String DEFAULT_ALGORITHM_TYPE = "md5";

	public static void main( String... parameterList ){
		int parameterListLength = parameterList.length;
		if( parameterListLength == 0 ||
			parameterListLength > 6 )
		{
			Exception exception = new Exception( "invalid parameter list" );
			System.err.print( exception.getMessage( ) );

			return;
		}

		String hash = parameterList[ 0 ];
		String dictionary = parameterList[ 1 ];

		String separator = EMPTY_STRING;

		String startIndex = null;
		if( parameterListLength >= 3 && 
			parameterList[ 2 ].matches( "\\d+" ) )
		{
			startIndex = parameterList[ 2 ];

		}else if( parameterListLength == 3 ){
			separator = parameterList[ 2 ];	
		}

		String endIndex = null;
		if( parameterListLength >= 4 &&
			parameterList[ 3 ].matches( "\\d+" ) )
		{
			endIndex = parameterList[ 3 ];

		}else if( parameterListLength == 4 ){
			separator = parameterList[ 3 ];
		}

		String algorithmType = DEFAULT_ALGORITHM_TYPE;
		if( parameterListLength >= 5 &&
			parameterList[ 4 ].toLowerCase( ).matches( "md5|sha" ) )
		{
			algorithmType = parameterList[ 4 ];

		}else if( parameterListLength == 5 ){
			separator = parameterList[ 4 ];
		}
		
		if( parameterListLength == 6 ){
			separator = parameterList[ 5 ];
		}

		try{
			String revertedHash = revertHashAtRange( hash, dictionary, startIndex, endIndex, algorithmType, separator );	
			
			if( revertedHash.equals( null ) ){
				System.out.print( "@null" );
				
			}else{
				System.out.print( revertedHash );	
			}

		}catch( Exception exception ){
			System.err.print( exception.getMessage( ) );
		}
	}

	public static final String revertHashAtRange( String hash, String dictionary, String startIndex, String endIndex, String algorithmType, String separator )
		throws Exception
	{
		BigDecimal startingIndex = new BigDecimal( startIndex );
		BigDecimal endingIndex = new BigDecimal( endIndex );

		String sequence = null;
		String testingHash = null;

		MessageDigest messageDigest = MessageDigest.getInstance( algorithmType );
		for(
			BigDecimal index = startingIndex;
			index.compareTo( endingIndex ) < 0;
			index = index.add( BigDecimal.ONE )
		){
			sequence = convertToSequence( index.toString( ), dictionary, separator );

			testingHash = new BigInteger( 1, messageDigest.digest( sequence.getBytes( ) ) ).toString( 16 );

			if( hash.equals( testingHash ) ){
				return sequence;
			}
		}

		return null;
	}
}