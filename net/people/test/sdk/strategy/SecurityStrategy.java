package net.people.test.sdk.strategy;

import net.people.test.sdk.rule.ISecurityStrategy;

public class SecurityStrategy implements ISecurityStrategy {

    private static final String AES = "aes";
    private static final String DES = "des";
    private String mPolicy;

    public SecurityStrategy(String policy) {
        mPolicy = policy;
    }

    @Override
    public Object encrypt(String data) {

        return get().encrypt(data);
    }

    private ISecurityStrategy get() {
        switch (mPolicy) {
            case AES:
                return new AESStrategy();
            case DES:
                return new DESStrategy();
            default:
                return new NoStrategy();
        }
    }


}

