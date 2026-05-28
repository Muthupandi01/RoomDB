package com.example.roomdbkotlin.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roomdbkotlin.data.utils.Resource
import com.example.roomdbkotlin.presentation.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel()
) {

    var title by rememberSaveable {
        mutableStateOf("")
    }

    var content by rememberSaveable {
        mutableStateOf("")
    }

    var category by rememberSaveable {
        mutableStateOf("Personal")
    }

    var search by rememberSaveable {
        mutableStateOf("")
    }

    var editId by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    val state by viewModel.notesState.collectAsState()

    val snackbarHostState =
        remember {
            SnackbarHostState()
        }

    val scope = rememberCoroutineScope()

    var snackbarColor by remember {
        mutableStateOf(
            Color(0xFF2E7D32)
        )
    }

    val notes =
        (state as? Resource.Success)?.data
            ?: emptyList()

    Scaffold(

        containerColor = Color(0xFFF7F8FC),

        snackbarHost = {

            SnackbarHost(
                hostState = snackbarHostState
            ) { data ->

                Snackbar(

                    snackbarData = data,

                    containerColor =
                        snackbarColor,

                    contentColor = Color.White,

                    shape =
                        RoundedCornerShape(18.dp)

                )
            }
        },

        floatingActionButton = {

            FloatingActionButton(

                onClick = {

                    title = ""
                    content = ""
                    category = "Personal"
                    editId = null

                    showDialog = true
                },

                containerColor =
                    Color(0xFF6C63FF),

                shape = CircleShape

            ) {

                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            // TOP BAR

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "Welcome Back 👋",
                        style =
                            MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "My Notes",
                        style =
                            MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text =
                            "Manage your daily thoughts, tasks, ideas and important notes in one beautiful place.",

                        style =
                            MaterialTheme.typography.bodyMedium,

                        color =
                            Color.Gray.copy(alpha = 0.9f),

                        lineHeight = MaterialTheme
                            .typography
                            .bodyMedium
                            .lineHeight
                    )
                }


            }

            Spacer(modifier = Modifier.height(24.dp))

            // SEARCH

            ElevatedCard(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(24.dp),

                elevation =
                    CardDefaults.elevatedCardElevation(
                        defaultElevation = 4.dp
                    ),

                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.White
                )
            ) {

                OutlinedTextField(
                    value = search,
                    onValueChange = {

                        search = it
                        viewModel.onSearchChange(it)
                    },

                    modifier = Modifier.fillMaxWidth(),

                    placeholder = {
                        Text("Search notes...")
                    },

                    singleLine = true,

                    shape =
                        RoundedCornerShape(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // CATEGORY

            LazyRow(
                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                items(
                    listOf(
                        "Personal",
                        "Work",
                        "Ideas",
                        "Study"
                    )
                ) { item ->

                    FilterChip(
                        selected = category == item,

                        onClick = {
                            category = item
                        },

                        label = {
                            Text(item)
                        },

                        shape =
                            RoundedCornerShape(50.dp),

                        border = BorderStroke(
                            1.dp,
                            if (category == item)
                                Color(0xFF6C63FF)
                            else
                                Color.LightGray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "${notes.size} Notes",
                style =
                    MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            when (state) {

                is Resource.Loading -> {

                    Box(
                        modifier =
                            Modifier.fillMaxSize(),
                        contentAlignment =
                            Alignment.Center
                    ) {

                        CircularProgressIndicator()
                    }
                }

                is Resource.Error -> {

                    Text(
                        text =
                            state.message ?: "Error"
                    )
                }

                is Resource.Success -> {

                    if (notes.isEmpty()) {

                        Box(
                            modifier =
                                Modifier.fillMaxSize(),
                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                "No Notes Yet",
                                style =
                                    MaterialTheme
                                        .typography
                                        .titleLarge
                            )
                        }

                    } else {
                        val favoriteNotes =
                            notes.filter {
                                it.isFavorite
                            }

                        if (favoriteNotes.isNotEmpty()) {

                            Text(
                                text = "⭐ Favorites",
                                style =
                                    MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            LazyRow(
                                horizontalArrangement =
                                    Arrangement.spacedBy(12.dp)
                            ) {

                                items(favoriteNotes) { note ->

                                    ElevatedCard(

                                        modifier = Modifier
                                            .width(260.dp),

                                        shape =
                                            RoundedCornerShape(24.dp),

                                        colors =
                                            CardDefaults.elevatedCardColors(
                                                containerColor =
                                                    Color(0xFFFFF8E1)
                                            )

                                    ) {

                                        Column(
                                            modifier =
                                                Modifier.padding(18.dp)
                                        ) {

                                            Row(
                                                modifier =
                                                    Modifier.fillMaxWidth(),

                                                horizontalArrangement =
                                                    Arrangement.SpaceBetween,

                                                verticalAlignment =
                                                    Alignment.CenterVertically
                                            ) {

                                                Text(
                                                    text = note.title,

                                                    style =
                                                        MaterialTheme
                                                            .typography
                                                            .titleMedium,

                                                    fontWeight =
                                                        FontWeight.Bold,

                                                    modifier =
                                                        Modifier.weight(1f)
                                                )

                                                Icon(
                                                    Icons.Default.Star,
                                                    contentDescription = null,
                                                    tint =
                                                        Color(0xFFFFC107)
                                                )
                                            }

                                            Spacer(
                                                modifier =
                                                    Modifier.height(10.dp)
                                            )

                                            Text(
                                                text = note.content,
                                                maxLines = 3,
                                                color = Color.DarkGray
                                            )

                                            Spacer(
                                                modifier =
                                                    Modifier.height(14.dp)
                                            )

                                            Text(
                                                text = note.category,

                                                color =
                                                    Color(0xFF6C63FF),

                                                fontWeight =
                                                    FontWeight.SemiBold
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(
                                modifier = Modifier.height(24.dp)
                            )
                        }
                        LazyColumn(

                            modifier = Modifier.fillMaxSize(),

                            verticalArrangement =
                                Arrangement.spacedBy(14.dp),

                            contentPadding = PaddingValues(
                                top = 4.dp,
                                bottom = 120.dp
                            )

                        ) {

                            items(notes) { note ->

                                ElevatedCard(

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .shadow(
                                            8.dp,
                                            RoundedCornerShape(
                                                28.dp
                                            )
                                        ),

                                    shape =
                                        RoundedCornerShape(
                                            28.dp
                                        ),

                                    elevation =
                                        CardDefaults
                                            .elevatedCardElevation(
                                                defaultElevation = 3.dp
                                            ),

                                    colors =
                                        CardDefaults.elevatedCardColors(
                                            containerColor = Color.White
                                        )

                                ) {

                                    Column(
                                        modifier =
                                            Modifier.padding(
                                                20.dp
                                            )
                                    ) {

                                        Row(
                                            modifier =
                                                Modifier.fillMaxWidth(),

                                            horizontalArrangement =
                                                Arrangement.SpaceBetween,

                                            verticalAlignment =
                                                Alignment.CenterVertically
                                        ) {

                                            Column(
                                                modifier =
                                                    Modifier.weight(1f)
                                            ) {

                                                Text(
                                                    text =
                                                        note.title,

                                                    style =
                                                        MaterialTheme
                                                            .typography
                                                            .titleLarge,

                                                    fontWeight =
                                                        FontWeight.Bold
                                                )

                                                Spacer(
                                                    modifier =
                                                        Modifier.height(
                                                            4.dp
                                                        )
                                                )

                                                Text(
                                                    text =
                                                        note.category,

                                                    color =
                                                        Color(
                                                            0xFF6C63FF
                                                        )
                                                )
                                            }

                                            Row(
                                                verticalAlignment =
                                                    Alignment.CenterVertically
                                            ) {

                                                // FAVORITE

                                                IconButton(
                                                    onClick = {
                                                        viewModel.toggleFavorite(
                                                            note
                                                        )
                                                    }
                                                ) {

                                                    Icon(

                                                        imageVector =
                                                            if (note.isFavorite)
                                                                Icons.Default.Star
                                                            else
                                                                Icons.Outlined.Star,

                                                        contentDescription = null,

                                                        tint =
                                                            if (note.isFavorite)
                                                                Color(
                                                                    0xFFFFC107
                                                                )
                                                            else
                                                                Color.Gray
                                                    )
                                                }

                                                Box(
                                                    modifier =
                                                        Modifier
                                                            .clip(
                                                                RoundedCornerShape(
                                                                    50.dp
                                                                )
                                                            )
                                                            .background(
                                                                Color(
                                                                    0xFFEDEBFF
                                                                )
                                                            )
                                                            .padding(
                                                                horizontal = 14.dp,
                                                                vertical = 7.dp
                                                            )
                                                ) {

                                                    Text(
                                                        text =
                                                            note.category,

                                                        color =
                                                            Color(
                                                                0xFF6C63FF
                                                            ),

                                                        fontWeight =
                                                            FontWeight.SemiBold
                                                    )
                                                }
                                            }
                                        }

                                        Spacer(
                                            modifier =
                                                Modifier.height(
                                                    14.dp
                                                )
                                        )

                                        Text(
                                            text =
                                                note.content,

                                            style =
                                                MaterialTheme
                                                    .typography
                                                    .bodyLarge,

                                            color =
                                                Color.DarkGray
                                        )

                                        Spacer(
                                            modifier =
                                                Modifier.height(
                                                    18.dp
                                                )
                                        )

                                        Row(
                                            modifier =
                                                Modifier.fillMaxWidth(),

                                            horizontalArrangement =
                                                Arrangement.End
                                        ) {

                                            IconButton(
                                                onClick = {

                                                    title =
                                                        note.title

                                                    content =
                                                        note.content

                                                    category =
                                                        note.category

                                                    editId =
                                                        note.id

                                                    showDialog =
                                                        true
                                                }
                                            ) {

                                                Icon(
                                                    Icons.Default.Edit,
                                                    contentDescription = null,
                                                    tint =
                                                        Color(
                                                            0xFF1565C0
                                                        )
                                                )
                                            }

                                            IconButton(
                                                onClick = {

                                                    viewModel.delete(
                                                        note
                                                    )

                                                    snackbarColor =
                                                        Color(
                                                            0xFFC62828
                                                        )

                                                    scope.launch {

                                                        snackbarHostState
                                                            .showSnackbar(
                                                                "Note Deleted"
                                                            )
                                                    }
                                                }
                                            ) {

                                                Icon(
                                                    Icons.Default.Delete,
                                                    contentDescription = null,
                                                    tint = Color.Red
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // DIALOG

        if (showDialog) {

            AlertDialog(

                onDismissRequest = {
                    showDialog = false
                },

                confirmButton = {

                    Button(
                        onClick = {

                            if (title.isBlank()) {

                                snackbarColor =
                                    Color(0xFFEF6C00)

                                scope.launch {

                                    snackbarHostState
                                        .showSnackbar(
                                            "Title required"
                                        )
                                }

                                return@Button
                            }

                            if (content.isBlank()) {

                                snackbarColor =
                                    Color(0xFFEF6C00)

                                scope.launch {

                                    snackbarHostState
                                        .showSnackbar(
                                            "Content required"
                                        )
                                }

                                return@Button
                            }

                            if (editId == null) {

                                viewModel.insert(
                                    title,
                                    content,
                                    category
                                )

                                snackbarColor =
                                    Color(0xFF2E7D32)

                                scope.launch {

                                    snackbarHostState
                                        .showSnackbar(
                                            "Note Added Successfully"
                                        )
                                }

                            } else {

                                viewModel.update(
                                    id = editId!!,
                                    title = title,
                                    content = content,
                                    category = category
                                )

                                snackbarColor =
                                    Color(0xFF1565C0)

                                scope.launch {

                                    snackbarHostState
                                        .showSnackbar(
                                            "Note Updated Successfully"
                                        )
                                }
                            }

                            title = ""
                            content = ""
                            editId = null
                            showDialog = false
                        }
                    ) {

                        Text("Save")
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = {
                            showDialog = false
                        }
                    ) {

                        Text("Cancel")
                    }
                },

                title = {

                    Text(
                        if (editId == null)
                            "Add Note"
                        else
                            "Edit Note"
                    )
                },

                text = {

                    Column {

                        OutlinedTextField(
                            value = title,
                            onValueChange = {
                                title = it
                            },
                            label = {
                                Text("Title")
                            }
                        )

                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )

                        OutlinedTextField(
                            value = content,
                            onValueChange = {
                                content = it
                            },
                            label = {
                                Text("Content")
                            },
                            modifier =
                                Modifier.height(120.dp)
                        )
                    }
                }
            )
        }
    }
}