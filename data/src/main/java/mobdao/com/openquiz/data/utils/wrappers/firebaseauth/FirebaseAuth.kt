package mobdao.com.openquiz.data.utils.wrappers.firebaseauth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuth {
    fun getCurrentUser(): FirebaseUser?
    fun signInWithCredential(credential: AuthCredential): Task<AuthResult>
    fun signOut()
}
