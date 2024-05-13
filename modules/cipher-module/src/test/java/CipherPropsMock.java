import com.sensilabs.projecthub.cipher.CipherProps;

public class CipherPropsMock implements CipherProps {

    @Override
    public String getSecretKey() {
        return "AxjdS23jdApPOsdfj@345kaKscjdAksR";
    }

    @Override
    public String getAlgorithmType() {
        return "AES";
    }

    @Override
    public Integer getBufferSize() {
        return 1024;
    }
}
