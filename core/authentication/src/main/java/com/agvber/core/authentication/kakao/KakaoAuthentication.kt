package com.agvber.core.authentication.kakao

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.agvber.core.authentication.model.Token
import com.teampatch.core.common.getCustomParcelableExtra

/**
 * 카카오 로그인 페이지 결과 값을 callback 통하여 리턴 받는 함수 입니다.
 *
 * ```
 * @Composable
 * fun Function(modifier: Modifier = Modifier) {
 *     val launcher = rememberLauncherForKakaoLoginResult { result: Result<Token> ->
 *         result.onSuccess {
 *             // 서버 전달
 *             sendServer(it.userId, it.accessToken)
 *         }
 *             .onFailure {
 *                 Log.d(TAG, "로그인 에러...")
 *             }
 *     }
 *
 *     Box(
 *         contentAlignment = Alignment.Center,
 *         modifier = Modifier.fillMaxSize()
 *     ) {
 *         Button(
 *             onClick = {
 *                 // 카카오 로그인 페이지 열기
 *                 launcher.loginKakao(this@HiltPreviewActivity)
 *             }
 *         ) {
 *             Text("Login!")
 *         }
 *     }
 * }
 * ```
 *
 * @param callback 사용자의 카카오 토큰 값이 전달됩니다.
 */

@Composable
fun rememberLauncherForKakaoLoginResult(
    callback: (Result<Token>) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        runCatching {
            val intent = result.data!!
            intent.getCustomParcelableExtra(
                name = KakaoLoginHelperActivity.TOKEN_PARAM,
                clazz = Token::class.java
            )!!
        }
            .let(callback)
    }
}

/**
 * 카카오 로그인 페이지를 실행하는 함수 입니다.
 */

fun ManagedActivityResultLauncher<Intent, ActivityResult>.loginKakao(
    context: Context
) {
    val intent = Intent(context, KakaoLoginHelperActivity::class.java)
    launch(intent)
}