package net.people.test.sdk.strategy;

import net.people.test.sdk.rule.ISecurityStrategy;

public class NoStrategy implements ISecurityStrategy {
    @Override
    public String encrypt(String data) {
        return data;
    }
}
