import React, { useState } from 'react';
import {
    DragDropContext,
    Droppable,
    Draggable
} from 'react-beautiful-dnd';
import './ScheduleBuilder.css';

const initial = [
    { id:'c1', content:'Placeholder 1' },
    { id:'c2', content:'Placeholder 2' },
    { id:'c3', content:'Placeholder 3' },
    { id:'c4', content:'Placeholder 4' },
];

export default function ScheduleBuilder({ numSemesters }) {
    const [cols, setCols] = useState(() => {
        const out = { available:{ title:'Available Classes', items:initial } };
        for(let i=1; i<=numSemesters; i++){
            out[`sem${i}`] = { title:`Semester ${i}`, items:[] };
        }
        return out;
    });

    const onDragEnd = ({ source, destination }) => {
        if(!destination) return;
        const sc = cols[source.droppableId];
        const dc = cols[destination.droppableId];
        const sItems = Array.from(sc.items);
        const [m] = sItems.splice(source.index,1);
        const dItems = Array.from(dc.items);
        dItems.splice(destination.index,0,m);
        setCols({
            ...cols,
            [source.droppableId]: { ...sc, items:sItems },
            [destination.droppableId]: { ...dc, items:dItems }
        });
    };

    return (
        <div>
            <h2>Build Your Schedule</h2>
            <DragDropContext onDragEnd={onDragEnd}>
                <div className="grid">
                    {Object.entries(cols).map(([key,{title,items}])=>(
                        <Droppable droppableId={key} key={key}>
                            {prov=>(
                                <div
                                    className={key==='available'?'list':'box'}
                                    ref={prov.innerRef}
                                    {...prov.droppableProps}
                                >
                                    <h3>{title}</h3>
                                    {items.map((itm,i)=>(
                                        <Draggable key={itm.id} draggableId={itm.id} index={i}>
                                            {p=>(
                                                <div
                                                    className="item"
                                                    ref={p.innerRef}
                                                    {...p.draggableProps}
                                                    {...p.dragHandleProps}
                                                >
                                                    {itm.content}
                                                </div>
                                            )}
                                        </Draggable>
                                    ))}
                                    {prov.placeholder}
                                </div>
                            )}
                        </Droppable>
                    ))}
                </div>
            </DragDropContext>
        </div>
    );
}
