// src/App.jsx
import React from 'react';
import { ThemeProvider, CssBaseline } from '@mui/material';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import EquipamentosTable from './components/EquipamentosTable';
import UploadPage from './pages/UploadPage';
import unimedTheme from './theme/unimedTheme';
import AutorizacaoPage from "./pages/AutorizacaoPage.jsx";

function App() {
    return (
        <ThemeProvider theme={unimedTheme}>
            <CssBaseline />
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<EquipamentosTable />} />
                    <Route path="/upload" element={<UploadPage />} />
                    <Route path="/autorizar" element={<AutorizacaoPage />} />
                </Routes>
            </BrowserRouter>
        </ThemeProvider>
    );
}

export default App;
