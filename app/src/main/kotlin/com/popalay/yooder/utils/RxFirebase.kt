package com.popalay.yooder.utils

import com.google.firebase.FirebaseException
import com.google.firebase.database.*
import rx.Observable
import rx.functions.Func1
import rx.subscriptions.Subscriptions

object RxFirebase {

    enum class EventType {
        CHILD_CHANGED,
        CHILD_ADDED,
        CHILD_MOVED,
        CHILD_REMOVED
    }

    fun observeChildren(ref: Query): Observable<FirebaseChildEvent> {
        return Observable.create { subscriber ->
            val listener = ref.addChildEventListener(
                    object : ChildEventListener {
                        override fun onChildAdded(dataSnapshot: DataSnapshot, prevName: String) {
                            subscriber.onNext(FirebaseChildEvent(dataSnapshot,
                                    EventType.CHILD_ADDED, prevName))
                        }

                        override fun onChildChanged(dataSnapshot: DataSnapshot, prevName: String) {
                            subscriber.onNext(FirebaseChildEvent(dataSnapshot,
                                    EventType.CHILD_CHANGED, prevName))
                        }

                        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                            subscriber.onNext(FirebaseChildEvent(dataSnapshot,
                                    EventType.CHILD_REMOVED, null))
                        }

                        override fun onChildMoved(dataSnapshot: DataSnapshot, prevName: String) {
                            subscriber.onNext(FirebaseChildEvent(dataSnapshot,
                                    EventType.CHILD_MOVED, prevName))
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Turn the FirebaseError into a throwable to conform to the API
                            subscriber.onError(FirebaseException(error.message))
                        }
                    })

            // When the subscription is cancelled, remove the listener
            subscriber.add(Subscriptions.create { ref.removeEventListener(listener) })
        }
    }

    fun observeChildAdded(ref: Query): Observable<FirebaseChildEvent> {
        return observeChildren(ref).filter(makeEventFilter(EventType.CHILD_ADDED))
    }

    fun observeChildChanged(ref: Query): Observable<FirebaseChildEvent> {
        return observeChildren(ref).filter(makeEventFilter(EventType.CHILD_CHANGED))
    }

    fun observeChildMoved(ref: Query): Observable<FirebaseChildEvent> {
        return observeChildren(ref).filter(makeEventFilter(EventType.CHILD_MOVED))
    }

    fun observeChildRemoved(ref: Query): Observable<FirebaseChildEvent> {
        return observeChildren(ref).filter(makeEventFilter(EventType.CHILD_REMOVED))
    }

    fun observe(ref: Query): Observable<DataSnapshot> {

        return Observable.create { subscriber ->
            val listener = ref.addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            subscriber.onNext(dataSnapshot)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Turn the FirebaseError into a throwable to conform to the API
                            subscriber.onError(FirebaseException(error.message))
                        }
                    })

            // When the subscription is cancelled, remove the listener
            subscriber.add(Subscriptions.create { ref.removeEventListener(listener) })
        }
    }

    fun observeSingleValue(ref: Query): Observable<DataSnapshot> {

        return Observable.create { subscriber ->
            val listener = ref.addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            subscriber.onNext(dataSnapshot)
                            subscriber.onCompleted()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Turn the FirebaseError into a throwable to conform to the API
                            subscriber.onError(FirebaseException(error.message))
                        }
                    })

            // When the subscription is cancelled, remove the listener
            subscriber.add(Subscriptions.create { ref.removeEventListener(listener) })
        }
    }

    private fun makeEventFilter(eventType: EventType): Func1<FirebaseChildEvent, Boolean> {
        return Func1 { firebaseChildEvent -> firebaseChildEvent.eventType == eventType }
    }

    /**
     * Essentially a struct so that we can pass all children events through as a single object.
     */
    class FirebaseChildEvent(var snapshot: DataSnapshot,
                             var eventType: EventType,
                             var prevName: String?)
}