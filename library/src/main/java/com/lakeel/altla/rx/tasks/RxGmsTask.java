package com.lakeel.altla.rx.tasks;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import android.support.annotation.NonNull;

import rx.Completable;
import rx.CompletableSubscriber;
import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;

/**
 * Provides methods to wrap {@link Task} in a Rx object.
 */
public final class RxGmsTask {

    private RxGmsTask() {
    }

    /**
     * Wraps the specified {@link Task} in {@link Observable}.
     *
     * @param task      The wrapped task.
     * @param <TResult> The generic parameter of the specified task.
     * @return The {@link Observable} that wraps the specified {@link Task}.
     */
    public static <TResult> Observable<TResult> asObservable(final Task<TResult> task) {
        return Observable.create(new Observable.OnSubscribe<TResult>() {
            @Override
            public void call(final Subscriber<? super TResult> subscriber) {
                task.addOnSuccessListener(new OnSuccessListener<TResult>() {
                    @Override
                    public void onSuccess(TResult result) {
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }
                });
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        subscriber.onError(e);
                    }
                });
            }
        });
    }

    /**
     * Wraps the specified {@link Task} in {@link Single}.
     *
     * @param task      The wrapped task.
     * @param <TResult> The generic parameter of the specified task.
     * @return The {@link Single} that wraps the specified {@link Task}.
     */
    public static <TResult> Single<TResult> asSingle(final Task<TResult> task) {
        return Single.create(new Single.OnSubscribe<TResult>() {
            @Override
            public void call(final SingleSubscriber<? super TResult> subscriber) {
                task.addOnSuccessListener(new OnSuccessListener<TResult>() {
                    @Override
                    public void onSuccess(TResult result) {
                        subscriber.onSuccess(result);
                    }
                });
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        subscriber.onError(e);
                    }
                });
            }
        });
    }

    /**
     * Wraps the specified {@link Task} in {@link Completable}.
     *
     * @param task      The wrapped task.
     * @param <TResult> The generic parameter of the specified task.
     * @return The {@link Completable} that wraps the specified {@link Task}.
     */
    public static <TResult> Completable asCompletable(final Task<TResult> task) {
        return Completable.create(new Completable.OnSubscribe() {
            @Override
            public void call(final CompletableSubscriber subscriber) {
                task.addOnSuccessListener(new OnSuccessListener<TResult>() {
                    @Override
                    public void onSuccess(TResult tResult) {
                        subscriber.onCompleted();
                    }
                });
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        subscriber.onError(e);
                    }
                });
            }
        });
    }
}
