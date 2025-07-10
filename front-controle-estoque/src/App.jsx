// src/App.jsx
import React from 'react';
import { ThemeProvider, CssBaseline } from '@mui/material';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import EquipamentosTable from './pages/EquipamentosTable.jsx';
import UploadPage from './pages/UploadPage';
import unimedTheme from './theme/unimedTheme';
import AutorizacaoPage from "./pages/AutorizacaoPage.jsx";
import HomePage from "./pages/HomePage.jsx";

function App() {
    return (
        <ThemeProvider theme={unimedTheme}>
            <CssBaseline />
            <BrowserRouter>
                <Routes>
                    <Route path="/tabela" element={<EquipamentosTable />}></Route>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/upload" element={<UploadPage />} />
                    <Route path="/autorizar" element={<AutorizacaoPage />} />
                </Routes>
            </BrowserRouter>
        </ThemeProvider>
    );
}

export default App;
