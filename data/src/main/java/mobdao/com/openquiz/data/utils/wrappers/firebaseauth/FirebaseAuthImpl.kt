package mobdao.com.openquiz.data.utils.wrappers.firebaseauth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth as wrappedObject

class FirebaseAuthImpl : FirebaseAuth {

    override fun getCurrentUser(): FirebaseUser? =
        wrappedObject.getInstance().currentUser

    override fun signInWithCredential(credential: AuthCredential) =
        wrappedObject.getInstance().signInWithCredential(credential)

    override fun signOut() {
        wrappedObject.getInstance().signOut()
    }
}