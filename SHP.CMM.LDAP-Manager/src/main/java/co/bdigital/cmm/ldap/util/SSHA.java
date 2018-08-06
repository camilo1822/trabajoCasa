package co.bdigital.cmm.ldap.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import co.bdigital.cmm.ldap.util.Constant;
import co.bdigital.shl.tracer.CustomLogger;

public class SSHA {
    private static BASE64Encoder enc = new BASE64Encoder();
    private static BASE64Decoder dec = new BASE64Decoder();

    private final String ERROR_MESSAGE = "IOException SSHA Library";
    private boolean verbose = false;

    private CustomLogger logger;

    private MessageDigest sha = null;

    private static SSHA inst = new SSHA("SHA");

    public static SSHA SHA1 = new SSHA("SHA");

    public static SSHA SHA2 = new SSHA("SHA-256");

    private static String hexits = "0123456789abcdef";

    int size = 20;

    /**
     * public constructor
     */
    public SSHA(String alg) {
        verbose = false;
        this.logger = new CustomLogger(SSHA.class);
        if (alg.endsWith("256")) {
            size = 32;
        }
        if (alg.endsWith("512")) {
            size = 64;
        }

        try {
            sha = MessageDigest.getInstance(alg);
        } catch (java.security.NoSuchAlgorithmException e) {
            logger.error(Constant.COMMON_ERROR_CONSTRUCTION_FAILED, e);
        }
    }

    public static SSHA getInstance() {
        return inst;
    }

    /**
     * @param shaEnc
     */
    public void setAlgorithm(String shaEnc) {
        inst = new SSHA(shaEnc);
    }

    /**
     * Create Digest for each entity values passed in
     *
     * @param salt
     *            String to set the base for the encryption
     * @param entity
     *            string to be encrypted
     * @return string representing the salted hash output of the encryption
     *         operation
     */
    public String createDigest(String salt, String entity) {
        return createDigest(salt.getBytes(), entity);
    }

    /**
     * Create Digest for each entity values passed in
     *
     * @param salt
     *            byte array to set the base for the encryption
     * @param entity
     *            string to be encrypted
     * @return string representing the salted hash output of the encryption
     *         operation
     */
    public String createDigest(byte[] salt, String entity) {
        
        String label = Constant.COMMON_MSG_SSHA;

        // Update digest object with byte array of the source clear text
        // string and the salt
        sha.reset();
        sha.update(entity.getBytes());
        sha.update(salt);

        // Complete hash computation, this results in binary data
        byte[] pwhash = sha.digest();

        if (verbose) {
            logger.debug(
                    LDAPCommonUtil.buildString(Constant.COMMON_MSG_PWHASH_HEX,
                            toHex(pwhash), Constant.COMMON_MSG_N));
            logger.debug(Constant.COMMON_MSG_ALL_TOGETHER);
            logger.debug(LDAPCommonUtil.buildString(
                    Constant.COMMON_MSG_PWHASH_AND_SALT, String.valueOf(pwhash),
                    String.valueOf(salt)));
            logger.debug(Constant.COMMON_MSG_PWHASH_AND_SALT_BASE64);
        }

        return LDAPCommonUtil.buildString(label,
                new String(enc.encode(concatenate(pwhash, salt))));
    }

    /**
     * Create Digest for each entity values passed in. A random salt is used.
     *
     * @param entity
     *            string to be encrypted
     * @return string representing the salted hash output of the encryption
     *         operation
     */
    public String createDigest(String entity) {
        return inst.createDigest(randSalt(), entity);
    }

    /**
     * set the verbose flag
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Combine two byte arrays
     *
     * @param l
     *            first byte array
     * @param r
     *            second byte array
     * @return byte[] combined byte array
     */
    private static byte[] concatenate(byte[] l, byte[] r) {
        byte[] b = new byte[l.length + r.length];
        System.arraycopy(l, 0, b, 0, l.length);
        System.arraycopy(r, 0, b, l.length, r.length);
        return b;
    }

    /**
     * Convert a byte array to a hex encoded string
     *
     * @param block
     *            byte array to convert to hexString
     * @return String representation of byte array
     */
    private static String toHex(byte[] block) {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < block.length; ++i) {
            buf.append(hexits.charAt((block[i] >>> 4) & 0xf));
            buf.append(hexits.charAt(block[i] & 0xf));
        }

        return buf + "";
    }

    public byte[] randSalt() {
        int saltLen = 4; // 8
        byte[] b = new byte[saltLen];
        for (int i = 0; i < saltLen; i++) {
            byte bt = (byte) (((Math.random()) * 256) - 128);
            b[i] = bt;
        }
        return b;
    }

    public String createDigest(String password, boolean salted)
            throws NoSuchAlgorithmException {
        MessageDigest digester = MessageDigest.getInstance("SHA");
        byte[] result;

        byte[] passwordBytes = password.getBytes();

        synchronized (digester) {
            digester.reset();
            if (salted) {
                // set salt
                byte[] salt = new byte[8];
                new SecureRandom().nextBytes(salt);
                digester.update(passwordBytes);
                digester.update(salt);
                byte[] hashedPassword = digester.digest();
                result = new byte[hashedPassword.length + salt.length];

                System.arraycopy(hashedPassword, 0, result, 0,
                        hashedPassword.length);
                System.arraycopy(salt, 0, result, hashedPassword.length,
                        salt.length);
            } else {
                result = digester.digest(passwordBytes);
            }
        }
        return enc.encodeBuffer(result);
    }

}