import javax.swing.JOptionPane

fun encryptRailFence(text: String, rails: Int): String {
    if (rails <= 1) return text

    val railLines = MutableList(rails) { StringBuilder() }
    var index = 0
    var step = 1

    text.forEach { char ->
        railLines[index].append(char)
        if (index == 0) step = 1
        if (index == rails - 1) step = -1
        index += step
    }

    return railLines.joinToString("") { it.toString() }
}

fun decryptRailFence(cipherText: String, rails: Int): String {
    if (rails <= 1) return cipherText

    val pattern = IntArray(cipherText.length)
    var index = 0
    var step = 1

    for (i in cipherText.indices) {
        pattern[i] = index
        if (index == 0) step = 1
        if (index == rails - 1) step = -1
        index += step
    }

    val railLines = MutableList(rails) { StringBuilder() }
    val sortedIndices = pattern.withIndex().sortedBy { it.value }
    var charIndex = 0

    for ((_, railIndex) in sortedIndices) {
        railLines[railIndex].append(cipherText[charIndex++])
    }

    val decryptedText = StringBuilder()
    for (i in pattern.indices) {
        decryptedText.append(railLines[pattern[i]].first())
        railLines[pattern[i]].deleteCharAt(0)
    }

    return decryptedText.toString()
}

fun main() {
    val options = arrayOf("Cifrar", "Descifrar", "Salir")

    while (true) {
        val choice = JOptionPane.showOptionDialog(
            null, "Seleccione una opción:", "Cifrado Rail Fence",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, options, options[0]
        )

        if (choice == 2 || choice == JOptionPane.CLOSED_OPTION) break

        val text = JOptionPane.showInputDialog("Ingrese el texto:")?.trim()
        if (text.isNullOrEmpty()) continue

        val railsInput = JOptionPane.showInputDialog("Ingrese el número de rieles:")
        val rails = railsInput?.toIntOrNull() ?: continue

        val result = if (choice == 0) encryptRailFence(text, rails)
        else decryptRailFence(text, rails)

        JOptionPane.showMessageDialog(null, "Resultado: $result")
    }
}
