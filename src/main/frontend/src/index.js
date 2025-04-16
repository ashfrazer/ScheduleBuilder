import React from 'react';
import { createRoot } from 'react-dom/client';
import SchedulerWizard from './components/SchedulerWizard';
import './index.css';

const container = document.getElementById('root');
const root = createRoot(container);

root.render(
    <React.StrictMode>
        <SchedulerWizard />
    </React.StrictMode>
);