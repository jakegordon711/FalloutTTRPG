import falloutLogo from '../resources/images/Fallout_logo.svg';
import Table from '../components/table.js';

export default function CharacterSheet(){
    return(
        <div className='characterSheet '>
            <div className='characterDetails flex m-8 border'>
                <div className='falloutLogoInfo flex flex-col w-1/5 items-center'>
                    <img src={falloutLogo} className='falloutLogo h-auto'/>
                    <div className='text-2xl'>THE ROLEPLAYING GAME</div>
                </div>
                <Table/>
            </div>
        </div>
    );
}