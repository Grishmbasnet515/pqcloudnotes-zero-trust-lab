package com.student.pqcloudnotes.ui.navigation

sealed class Route(val path: String) {
    data object Login : Route("login")
    data object Notes : Route("notes")
    data object NoteDetail : Route("note_detail")
    data object Settings : Route("settings")
}
