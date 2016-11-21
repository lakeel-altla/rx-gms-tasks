# rx-gms-tasks

rx-gms-tasks is a library that provides conversion from a Task object of GMS to a RxJava object.

## Usage

```java
import com.google.android.gms.tasks.Task;
import rx.Completable;
import rx.Observable;
import rx.Single;

...
Task<Void> task = ...

// to Observable
Observable observable = RxGmsTask.asObservable(task);

// to Single
Single single = RxGmsTask.asSingle(task);

// to Completable
Completable completable = RxGmsTask.asCompletable(task);
```
