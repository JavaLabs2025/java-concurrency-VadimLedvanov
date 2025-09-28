package org.labs.lab1.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@RequiredArgsConstructor
public class Request {
    private final int philosopherId;

    public final CompletableFuture<Boolean> completed = new CompletableFuture<>();
}
