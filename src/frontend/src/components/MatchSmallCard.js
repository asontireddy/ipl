import { React } from 'react';

export const MatchSmallCard = function MatchSmallCard({match}) {
    return (
        <div className="MatchSmallCard">
            <p>{match.team1} vs {match.team2}</p>
        </div>
    );
}