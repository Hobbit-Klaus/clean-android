package com.github.msbaek.rxessentials.user.domain;

import com.github.msbaek.rxessentials.common.mvp.UseCase;
import rx.Observable;

public class GetUserDetail extends UseCase<Object> {

    @Override
    protected Observable<Object> buildUseCaseObservable() {
        return Observable.empty();
    }
}
