import React, { useState } from 'react';
import ScheduleBuilder from './ScheduleBuilder';
import './SchedulerWizard.css';

export default function SchedulerWizard() {
    const [step, setStep] = useState(1);
    const [semesters, setSemesters] = useState(3);

    return (
        <div className="wizard">
            {step === 1 && (
                <div className="step">
                    <h2>How many semesters do you have left?</h2>
                    <select value={semesters}
                            onChange={e => setSemesters(+e.target.value)}>
                        {[1,2,3,4,5,6,7,8].map(n =>
                            <option key={n} value={n}>{n} semester{n>1?'s':''}</option>
                        )}
                    </select>
                    <button onClick={()=>setStep(2)}>Next</button>
                </div>
            )}
            {step === 2 && (
                <div className="step">
                    <h2>Ready? Let’s build your schedule:</h2>
                    <button onClick={()=>setStep(3)}>Go</button>
                </div>
            )}
            {step === 3 && (
                <ScheduleBuilder numSemesters={semesters} />
            )}
        </div>
    );
}
