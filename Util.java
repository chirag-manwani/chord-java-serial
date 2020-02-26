import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
  
public class Util { 
    public static String hash(String input) 
    { 
        try { 
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean in(BigInteger n, BigInteger l, BigInteger u) {
        if(l.compareTo(u) == 0 || l.compareTo(n) == 0 || u.compareTo(n) == 0) {
            return true;
        }

        if(l.compareTo(u) > 0) {
            return !in(n, u, l);
        }

        if(n.compareTo(l) > 0 && n.compareTo(u) < 0) {
            return true;
        }
        return false;
    }
}