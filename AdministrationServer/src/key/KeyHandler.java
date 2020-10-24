package key;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.time.ZonedDateTime;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import objects.LoginObject;


public class KeyHandler
{
	private String keyStorepswd = "123456789";
	private KeyStore kStore;
	private Key key;
	private PublicKey publicKey;
	
	public KeyHandler()
	{
		try 
		{
			kStore  = KeyStore.getInstance(KeyStore.getDefaultType());
			kStore.load(new FileInputStream("/home/bla/hanbotest.jks"), keyStorepswd.toCharArray());
			key = kStore.getKey("hanbotest", keyStorepswd.toCharArray());
			publicKey = loadPublicKey("/home/bla/hanbotest.cer");
		} catch (NoSuchAlgorithmException | CertificateException | IOException e)
		{
			e.printStackTrace();
		} catch (UnrecoverableKeyException e)
		{
			e.printStackTrace();
		} catch (KeyStoreException e) 
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//returns a public key to key handler
	private static PublicKey loadPublicKey(String filename)
			   throws Exception
			{
			   CertificateFactory cf = CertificateFactory.getInstance("X.509");
			   Certificate cert = cf.generateCertificate(new FileInputStream(filename));
			   PublicKey retVal = cert.getPublicKey();
			   return retVal;
			}
	
	//returns a key(token) (with expiration time)
	public String createToken(int id)
	{
		String compactJws = Jwts.builder()
				   .setExpiration(Date.from(ZonedDateTime.now().plusHours(1).toInstant()))
				   .setIssuedAt(new Date())
				   .setId(Integer.toString(id))
				   .signWith(SignatureAlgorithm.RS512, key)
				   .compact();
				   
				return compactJws;
	}
	
	//returns the company_Id from the token
	public int getCompany_Id(String token)
	{
		Jws<Claims> x = Jwts.parser()
				   .setSigningKey(publicKey)
				   .parseClaimsJws(token);
		
		return Integer.parseInt(x.getBody().getId());
	}
	
	//checks if the token is still valid
	public boolean isTokenValid(String token)
	{
		try 
		{
			Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {return false;}
		  catch (MalformedJwtException e) {return false;}
		  catch (IllegalArgumentException e) {return false;}
		  catch (SignatureException e) {return false;}
	}	
}
