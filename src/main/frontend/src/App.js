// src/main/frontend/src/App.js
import React, { useState } from 'react'
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd'

export default function App() {
    // 1 = pick semesters, 2 = pick min/max, 3 = pick conflict-days, 4 = drag & drop
    const [step, setStep] = useState(1)

    // form state:
    const [numSemesters, setNumSemesters]     = useState(3)
    const [minCredits, setMinCredits]         = useState(3)
    const [maxCredits, setMaxCredits]         = useState(12)
    const [conflictDays, setConflictDays]     = useState([])

    // a trivial placeholder list of “courses” for step 4
    const [courses, setCourses] = useState([
        { id: 'course-1', content: 'Placeholder 1' },
        { id: 'course-2', content: 'Placeholder 2' },
        { id: 'course-3', content: 'Placeholder 3' },
        { id: 'course-4', content: 'Placeholder 4' },
    ])

    // Handler for DnD:
    function onDragEnd(result) {
        if (!result.destination) return
        const srcIdx = result.source.index
        const dstIdx = result.destination.index
        const newArr = Array.from(courses)
        const [moved] = newArr.splice(srcIdx, 1)
        newArr.splice(dstIdx, 0, moved)
        setCourses(newArr)
    }

    return (
        <div style={{ padding: 20, fontFamily: 'sans‑serif' }}>

            {step === 1 && (
                <>
                    <h2>How many semesters do you have left?</h2>
                    <select
                        value={numSemesters}
                        onChange={e => setNumSemesters(parseInt(e.target.value))}
                    >
                        {[1,2,3,4,5,6,7,8].map(n =>
                            <option key={n} value={n}>{n} semester{n>1?'s':''}</option>
                        )}
                    </select>{' '}
                    <button onClick={()=>setStep(2)}>Next</button>
                    <p style={{ color: 'green' }}>
                        You have selected {numSemesters} semester(s) remaining.
                    </p>
                </>
            )}

            {step === 2 && (
                <>
                    <h2>
                        Awesome! Do you have a preference of minimum or maximum credit hours for each semester?
                    </h2>
                    <label>
                        Min credits:{' '}
                        <select
                            value={minCredits}
                            onChange={e=>setMinCredits(+e.target.value)}
                        >
                            {[3,6,9,12,15].map(n => <option key={n} value={n}>{n}</option>)}
                        </select>
                    </label>
                    {' '}
                    <label>
                        Max credits:{' '}
                        <select
                            value={maxCredits}
                            onChange={e=>setMaxCredits(+e.target.value)}
                        >
                            {[12,15,18,21].map(n => <option key={n} value={n}>{n}</option>)}
                        </select>
                    </label>
                    <br/><br/>
                    <button onClick={()=>setStep(3)}>Submit Preferences</button>
                    <p style={{ color:'green' }}>
                        You selected: Min {minCredits}, Max {maxCredits}.
                    </p>
                </>
            )}

            {step === 3 && (
                <>
                    <h2>
                        Great! Are there any days of the week that would conflict with your schedule?
                    </h2>
                    {['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday'].map(day => (
                        <div key={day}>
                            <label>
                                <input
                                    type="checkbox"
                                    checked={conflictDays.includes(day)}
                                    onChange={e=>{
                                        if (e.target.checked) {
                                            setConflictDays(d=>[...d,day])
                                        } else {
                                            setConflictDays(d=>d.filter(x=>x!==day))
                                        }
                                    }}
                                />{' '}
                                {day}
                            </label>
                        </div>
                    ))}
                    <br/>
                    <button onClick={()=>setStep(4)}>Submit Days</button>
                    <p style={{ color:'green' }}>
                        {conflictDays.length === 0
                            ? 'You did not select any days as conflicts.'
                            : 'Conflicts: ' + conflictDays.join(', ')
                        }
                    </p>
                </>
            )}

            {step === 4 && (
                <>
                    <h2>Build Your Schedule</h2>
                    <div style={{ display:'flex', gap:10 }}>
                        {Array.from({ length: numSemesters }).map((_, si) => (
                            <Droppable key={si} droppableId={`sem${si}`} >
                                {(provided) => (
                                    <div
                                        ref={provided.innerRef}
                                        {...provided.droppableProps}
                                        style={{
                                            minWidth: 200,
                                            minHeight: 200,
                                            border:'2px dashed #888',
                                            borderRadius:4,
                                            padding:10
                                        }}
                                    >
                                        <strong>Semester {si+1}</strong>
                                        {provided.placeholder}
                                    </div>
                                )}
                            </Droppable>
                        ))}
                    </div>

                    <h3>Available Classes</h3>
                    <DragDropContext onDragEnd={onDragEnd}>
                        <Droppable droppableId="classes">
                            {(prov) => (
                                <div ref={prov.innerRef} {...prov.droppableProps}>
                                    {courses.map((c,i) => (
                                        <Draggable key={c.id} draggableId={c.id} index={i}>
                                            {(p) => (
                                                <div
                                                    ref={p.innerRef}
                                                    {...p.draggableProps}
                                                    {...p.dragHandleProps}
                                                    style={{
                                                        padding:10,
                                                        margin:'4px 0',
                                                        background:'#007bff',
                                                        color:'white',
                                                        borderRadius:4,
                                                        ...p.draggableProps.style
                                                    }}
                                                >
                                                    {c.content}
                                                </div>
                                            )}
                                        </Draggable>
                                    ))}
                                    {prov.placeholder}
                                </div>
                            )}
                        </Droppable>
                    </DragDropContext>
                </>
            )}

        </div>
    )
}
