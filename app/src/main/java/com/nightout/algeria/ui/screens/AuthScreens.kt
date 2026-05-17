package com.nightout.algeria.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nightout.algeria.ui.theme.NeonPurple
import com.nightout.algeria.ui.viewmodel.AuthState
import com.nightout.algeria.ui.viewmodel.AuthViewModel

enum class Language(val code: String, val title: String) {
    EN("en", "English"),
    FR("fr", "Français"),
    AR("ar", "العربية")
}

var currentLanguage by mutableStateOf(Language.EN)

val localizedStrings = mapOf(
    Language.EN to mapOf(
        "welcome" to "Welcome Back",
        "email" to "Email",
        "password" to "Password",
        "login" to "Login",
        "forgot_pwd" to "Forgot Password?",
        "no_account" to "Don't have an account? Register",
        "create_account" to "Create Account",
        "full_name" to "Full Name",
        "register" to "Register",
        "has_account" to "Already have an account? Login",
        "reset_sent" to "Password reset email sent!",
        "enter_email" to "Please enter your email first",
        "detail" to "Details",
        "back" to "Back",
        "profile" to "Profile",
        "add_venue" to "Add Venue",
        "logout" to "Logout",
        "admin_dashboard" to "Admin Dashboard"
    ),
    Language.FR to mapOf(
        "welcome" to "Bon retour",
        "email" to "E-mail",
        "password" to "Mot de passe",
        "login" to "Se connecter",
        "forgot_pwd" to "Mot de passe oublié ?",
        "no_account" to "Pas de compte ? S'inscrire",
        "create_account" to "Créer un compte",
        "full_name" to "Nom complet",
        "register" to "S'inscrire",
        "has_account" to "Déjà un compte ? Se connecter",
        "reset_sent" to "E-mail de réinitialisation envoyé !",
        "enter_email" to "Veuillez d'abord saisir votre e-mail",
        "detail" to "Détails",
        "back" to "Retour",
        "profile" to "Profil",
        "add_venue" to "Ajouter un lieu",
        "logout" to "Déconnexion",
        "admin_dashboard" to "Tableau de Bord Admin"
    ),
    Language.AR to mapOf(
        "welcome" to "أهلاً بك مجدداً",
        "email" to "البريد الإلكتروني",
        "password" to "كلمة المرور",
        "login" to "تسجيل الدخول",
        "forgot_pwd" to "نسيت كلمة المرور؟",
        "no_account" to "ليس لديك حساب؟ سجل الآن",
        "create_account" to "إنشاء حساب",
        "full_name" to "الاسم الكامل",
        "register" to "تسجيل",
        "has_account" to "لديك حساب بالفعل؟ سجل دخولك",
        "reset_sent" to "تم إرسال رابط استعادة كلمة المرور!",
        "enter_email" to "الرجاء إدخال البريد الإلكتروني أولاً",
        "detail" to "التفاصيل",
        "back" to "رجوع",
        "profile" to "حسابي",
        "add_venue" to "إضافة مكان",
        "logout" to "تسجيل خروج",
        "admin_dashboard" to "لوحة تحكم الإدارة"
    )
)

fun getString(key: String): String {
    return localizedStrings[currentLanguage]?.get(key) ?: key
}

@Composable
fun LanguageSelector() {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Language.values().forEach { lang ->
            Text(
                text = lang.title,
                modifier = Modifier
                    .clickable { currentLanguage = lang }
                    .padding(horizontal = 8.dp),
                color = if (currentLanguage == lang) NeonPurple else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                fontWeight = if (currentLanguage == lang) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToAdmin: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()
    val resetState by viewModel.resetPasswordState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(resetState) {
        resetState?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.clearResetState()
        }
    }

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            val isAdmin = (authState as AuthState.Success).isAdmin
            viewModel.clearAuthState()
            if (isAdmin) onNavigateToAdmin() else onNavigateToHome()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LanguageSelector()

        Text(
            text = getString("welcome"),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(getString("email")) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = NeonPurple,
                focusedLabelColor = NeonPurple
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(getString("password")) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = NeonPurple,
                focusedLabelColor = NeonPurple
            )
        )
        
        Text(
            text = getString("forgot_pwd"),
            color = NeonPurple,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { viewModel.resetPassword(email.trim()) }
                .padding(bottom = 24.dp)
        )

        if (authState is AuthState.Error) {
            Text(
                text = (authState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = { viewModel.login(email.trim(), password) },
            modifier = Modifier.fillMaxWidth(0.8f).height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
            enabled = authState !is AuthState.Loading
        ) {
            if (authState is AuthState.Loading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
            } else {
                Text(getString("login"), fontSize = 16.sp)
            }
        }

        TextButton(
            onClick = onNavigateToRegister,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(getString("no_account"), color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            Toast.makeText(context, "Registration Successful!", Toast.LENGTH_SHORT).show()
            viewModel.clearAuthState()
            onNavigateToHome()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LanguageSelector()

        Text(
            text = getString("create_account"),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(getString("full_name")) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(getString("email")) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(getString("password")) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        )

        if (authState is AuthState.Error) {
            Text(
                text = (authState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = { viewModel.register(name.trim(), email.trim(), password) },
            modifier = Modifier.fillMaxWidth(0.8f).height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
            enabled = authState !is AuthState.Loading
        ) {
            if (authState is AuthState.Loading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
            } else {
                Text(getString("register"), fontSize = 16.sp)
            }
        }

        TextButton(onClick = onNavigateBack, modifier = Modifier.padding(top = 16.dp)) {
            Text(getString("has_account"), color = MaterialTheme.colorScheme.onBackground)
        }
    }
}
