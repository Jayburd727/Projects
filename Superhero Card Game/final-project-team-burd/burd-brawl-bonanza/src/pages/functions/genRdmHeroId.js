// At time of writing, the API consists of heroes ranging from ID 1 to 731

const maxAPIHeroId = 731;

function genRdmHeroId() {
  return Math.floor(Math.random() * maxAPIHeroId) + 1
}
    
module.exports = genRdmHeroId;