package goldrush.skeedromano.official.ui.theme

import androidx.compose.ui.graphics.Color
import goldrush.skeedromano.official.ui.theme.Palette

// Based on the colors: #E8F5FD, #CCE1EF, #9BC0DA, #689EC2, #1B5B7E
// No background was specified. The five colors already form a clean light-to-dark tonal
// ramp of one hue, so this reuses them directly as the surface ladder (lightest → darkest)
// instead of inventing new tones, the same way the original v_cp292 scheme works. Only
// secondary/tertiary needed two extra dark-blue shades, close to the given #1B5B7E, since
// the ramp has just one color dark enough to pair cleanly with white text.
val AppPalette = Palette(
    primary = Color(0xFF1B5B7E),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF9BC0DA),
    onPrimaryContainer = Color(0xFF1B5B7E),

    secondary = Color(0xFF2A6B8C),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCCE1EF),
    onSecondaryContainer = Color(0xFF1B5B7E),

    tertiary = Color(0xFF124A68),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFE8F5FD),
    onTertiaryContainer = Color(0xFF1B5B7E),

    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    background = Color(0xFFE8F5FD),
    onBackground = Color(0xFF1B5B7E),

    surface = Color(0xFFE8F5FD),
    onSurface = Color(0xFF1B5B7E),

    surfaceVariant = Color(0xFFCCE1EF),
    onSurfaceVariant = Color(0xFF3D6B85),

    surfaceTint = Color(0xFF1B5B7E),
    surfaceDim = Color(0xFFCCE1EF),
    surfaceBright = Color(0xFFFFFFFF),

    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFE8F5FD),
    surfaceContainer = Color(0xFFCCE1EF),
    surfaceContainerHigh = Color(0xFF9BC0DA),
    surfaceContainerHighest = Color(0xFF689EC2),

    outline = Color(0xFF1B5B7E),
    outlineVariant = Color(0xFFCCE1EF),

    inverseSurface = Color(0xFF1B5B7E),
    inverseOnSurface = Color(0xFFE8F5FD),
    inversePrimary = Color(0xFF689EC2),

    scrim = Color(0xFF000000),
)
