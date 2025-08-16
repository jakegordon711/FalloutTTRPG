export default function TableRow({tableRowInfo}){
    const rowDetails = [];

    console.log(tableRowInfo);
    console.log(tableRowInfo.rowSize);

    for(let rowIndex = 0; rowIndex < tableRowInfo.rowSize; rowIndex++){
        rowDetails.push(
            <div className='flex flex-auto items-center'>
                <div className='w-11/12'>
                    <textarea className='bg-sky-100 text-center'></textarea>
                </div>
            </div>
        );
    }

    return (
        <div className='tableRow flex flex-row divide-x divide-black items-center'>
            {rowDetails}
        </div>
    );
}