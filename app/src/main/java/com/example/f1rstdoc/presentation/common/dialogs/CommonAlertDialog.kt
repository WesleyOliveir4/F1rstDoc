import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.copy
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

enum class MessageType {
    LOADING,
    ERROR,
    INFO
}

@Composable
fun TransientBottomMessage(
    modifier: Modifier = Modifier,
    visible: Boolean,
    message: String,
    messageType: MessageType = MessageType.INFO,
    durationMillis: Long = 1500, // Duração padrão aumentada para melhor visualização
    onDismiss: () -> Unit
) {
    // Estado interno para controlar a visibilidade para a animação de desaparecimento
    var internalVisible by remember { mutableStateOf(visible) }

    // Sincroniza internalVisible com o parâmetro visible
    // e agenda o desaparecimento
    LaunchedEffect(visible, durationMillis) {
        if (visible) {
            internalVisible = true
            delay(durationMillis)
            internalVisible = false // Inicia a animação de saída
            // Aguarda a animação de saída antes de chamar onDismiss,
            // ajuste o delay para corresponder à duração da animação de fadeOut/slideOut
            delay(300L) // Duração da animação de fadeOut/slideOut
            onDismiss()
        } else {
            internalVisible = false
        }
    }


    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp), // Margem da borda inferior da tela
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = internalVisible,
            enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(animationSpec = tween(200)),
            exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(animationSpec = tween(300))
        ) {
            Card(
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 12.dp), // Bordas arredondadas
                colors = CardDefaults.cardColors(
                    containerColor = when (messageType) {
                        MessageType.ERROR -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.9f)
                        MessageType.LOADING -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
                        MessageType.INFO -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f)
                    }
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 16.dp
                )) {
                    if (messageType == MessageType.LOADING) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (message.isNotBlank()) {
                            Text( // Para o caso de LOADING com mensagem
                                text = message,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(start = 30.dp) // Espaço para o CircularProgressIndicator
                            )
                        }
                    } else {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = when (messageType) {
                                MessageType.ERROR -> MaterialTheme.colorScheme.onErrorContainer
                                else -> MaterialTheme.colorScheme.onPrimaryContainer
                            }
                        )
                    }
                }
            }
        }
    }
}