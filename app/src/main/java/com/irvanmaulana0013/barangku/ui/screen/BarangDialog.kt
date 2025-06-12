package com.irvanmaulana0013.barangku.ui.screen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.irvanmaulana0013.barangku.R
import com.irvanmaulana0013.barangku.model.Barang

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarangDialog(
    userId: String,
    bitmap: Bitmap?,
    barang: Barang?,
    viewModel: MainViewModel,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    var namaBarang by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("") }
    var jumlah by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val categories = listOf(
        stringResource(R.string.peralatan),
        stringResource(R.string.perlengkapan),
        stringResource(R.string.kendaraan),
        stringResource(R.string.pakaian),
        stringResource(R.string.elektronik),
        stringResource(R.string.lainnya)
    )

    LaunchedEffect(Unit) {
        if (barang == null) return@LaunchedEffect
        namaBarang = barang.namaBarang
        kategori = barang.kategori
        jumlah = barang.jumlah.toString()
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap!!.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                    )
                }
                else if (barang != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(barang.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(R.string.gambar, barang.namaBarang),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.loading_img),
                        error = painterResource(id = R.drawable.broken_img),
                        modifier = Modifier
                            .size(250.dp)
                            .padding(4.dp)
                    )
                }
                OutlinedTextField(
                    value = namaBarang,
                    onValueChange = { namaBarang = it },
                    label = { Text(text = stringResource(R.string.nama_barang)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    OutlinedTextField(
                        value = kategori,
                        onValueChange = {},
                        label = { Text(text = stringResource(R.string.kategori)) },
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        categories.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    kategori = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = jumlah,
                    onValueChange = { jumlah = it },
                    label = { Text(text = stringResource(R.string.jumlah)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(R.string.batal))
                    }
                    OutlinedButton(
                        onClick = {
                            if (barang == null) {
                                viewModel.saveData(userId, namaBarang, kategori, jumlah, bitmap!!)
                                onConfirmation()
                            }
                            else {
                                viewModel.editData(userId, barang.id, namaBarang, kategori, jumlah)
                                onConfirmation()
                            }
                        },
                        enabled = namaBarang.isNotEmpty() &&
                                kategori.isNotEmpty() && jumlah.isNotEmpty(),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(R.string.simpan))
                    }
                }
            }
        }
    }
}