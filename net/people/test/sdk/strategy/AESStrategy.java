package net.people.test.sdk.strategy;

import net.people.test.sdk.rule.ISecurityStrategy;

public class AESStrategy implements ISecurityStrategy {
    @Override
    public Object encrypt(String data) {
        return "经过AES处理之后的数据:" + data;
    }
}
