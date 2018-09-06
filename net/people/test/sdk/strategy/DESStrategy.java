package net.people.test.sdk.strategy;

import net.people.test.sdk.rule.ISecurityStrategy;

public class DESStrategy implements ISecurityStrategy {
    @Override
    public String encrypt(String data) {
        return "经过DES处理之后的数据:" + data;

    }
}
