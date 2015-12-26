package com.androidbook.fragments.bard;

public class Shakespeare {
    public static String TITLES[] = {
            "Henry IV (1)",
            "Henry V",
            "Henry VIII",
            "Romeo and Juliet",
            "Hamlet",
            "The Merchant of Venice",
            "Othello"
    };
    public static String DIALOGUE[] = {
        "So shaken as we are, so wan with care,\n"+
"Find we a time for frighted peace to pant,\n"+
"And breathe short-winded accents of new broils\n"+
"To be commenced in strands afar remote.\n"+
"No more the thirsty entrance of this soil\n"+
"Shall daub her lips with her own children's blood;\n"+
"Nor more shall trenching war channel her fields,\n"+
"Nor bruise her flowerets with the armed hoofs\n"+
"Of hostile paces: those opposed eyes,\n"+
"Which, like the meteors of a troubled heaven,\n"+
"All of one nature, of one substance bred,\n"+
"Did lately meet in the intestine shock\n"+
"And furious close of civil butchery\n"+
"Shall now, in mutual well-beseeming ranks,\n"+
"March all one way and be no more opposed\n"+
"Against acquaintance, kindred and allies:\n"+
"The edge of war, like an ill-sheathed knife,\n"+
"No more shall cut his master. Therefore, friends,\n"+
"As far as to the sepulchre of Christ,\n"+
"Whose soldier now, under whose blessed cross\n"+
"We are impressed and engaged to fight,\n"+
"Forthwith a power of English shall we levy;\n"+
"Whose arms were moulded in their mothers' womb\n"+
"To chase these pagans in those holy fields\n"+
"Over whose acres walk'd those blessed feet\n"+
"Which fourteen hundred years ago were nail'd\n"+
"For our advantage on the bitter cross.\n"+
"But this our purpose now is twelve month old,\n"+
"And bootless 'tis to tell you we will go:\n"+
"Therefore we meet not now. Then let me hear\n"+
"Of you, my gentle cousin Westmoreland,\n"+
"What yesternight our council did decree\n"+
"In forwarding this dear expedience.",
        "O for a Muse of fire, that would ascend\n"+
"The brightest heaven of invention,\n"+
"A kingdom for a stage, princes to act\n"+
"And monarchs to behold the swelling scene!\n"+
"Then should the warlike Harry, like himself,\n"+
"Assume the port of Mars; and at his heels,\n"+
"Leash'd in like hounds, should famine, sword and fire\n"+
"Crouch for employment. But pardon, and gentles all,\n"+
"The flat unraised spirits that have dared\n"+
"On this unworthy scaffold to bring forth\n"+
"So great an object: can this cockpit hold\n"+
"The vasty fields of France? or may we cram\n"+
"Within this wooden O the very casques\n"+
"That did affright the air at Agincourt?\n"+
"O, pardon! since a crooked figure may\n"+
"Attest in little place a million;\n"+
"And let us, ciphers to this great accompt,\n"+
"On your imaginary forces work.\n"+
"Suppose within the girdle of these walls\n"+
"Are now confined two mighty monarchies,\n"+
"Whose high upreared and abutting fronts\n"+
"The perilous narrow ocean parts asunder:\n"+
"Piece out our imperfections with your thoughts;\n"+
"Into a thousand parts divide on man,\n"+
"And make imaginary puissance;\n"+
"Think when we talk of horses, that you see them\n"+
"Printing their proud hoofs i' the receiving earth;\n"+
"For 'tis your thoughts that now must deck our kings,\n"+
"Carry them here and there; jumping o'er times,\n"+
"Turning the accomplishment of many years\n"+
"Into an hour-glass: for the which supply,\n"+
"Admit me Chorus to this history;\n"+
"Who prologue-like your humble patience pray,\n"+
"Gently to hear, kindly to judge, our play.",
        "I come no more to make you laugh: things now,\n"+
"That bear a weighty and a serious brow,\n"+
"Sad, high, and working, full of state and woe,\n"+
"Such noble scenes as draw the eye to flow,\n"+
"We now present. Those that can pity, here\n"+
"May, if they think it well, let fall a tear;\n"+
"The subject will deserve it. Such as give\n"+
"Their money out of hope they may believe,\n"+
"May here find truth too. Those that come to see\n"+
"Only a show or two, and so agree\n"+
"The play may pass, if they be still and willing,\n"+
"I'll undertake may see away their shilling\n"+
"Richly in two short hours. Only they\n"+
"That come to hear a merry bawdy play,\n"+
"A noise of targets, or to see a fellow\n"+
"In a long motley coat guarded with yellow,\n"+
"Will be deceived; for, gentle hearers, know,\n"+
"To rank our chosen truth with such a show\n"+
"As fool and fight is, beside forfeiting\n"+
"Our own brains, and the opinion that we bring,\n"+
"To make that only true we now intend,\n"+
"Will leave us never an understanding friend.\n"+
"Therefore, for goodness' sake, and as you are known\n"+
"The first and happiest hearers of the town,\n"+
"Be sad, as we would make ye: think ye see\n"+
"The very persons of our noble story\n"+
"As they were living; think you see them great,\n"+
"And follow'd with the general throng and sweat\n"+
"Of thousand friends; then in a moment, see\n"+
"How soon this mightiness meets misery:\n"+
"And, if you can be merry then, I'll say\n"+
"A man may weep upon his wedding-day.",
        "Two households, both alike in dignity,\n"+
"In fair Verona, where we lay our scene,\n"+
"From ancient grudge break to new mutiny,\n"+
"Where civil blood makes civil hands unclean.\n"+
"From forth the fatal loins of these two foes\n"+
"A pair of star-cross'd lovers take their life;\n"+
"Whose misadventured piteous overthrows\n"+
"Do with their death bury their parents' strife.\n"+
"The fearful passage of their death-mark'd love,\n"+
"And the continuance of their parents' rage,\n"+
"Which, but their children's end, nought could remove,\n"+
"Is now the two hours' traffic of our stage;\n"+
"The which if you with patient ears attend,\n"+
"What here shall miss, our toil shall strive to mend.",
        "Though yet of Hamlet our dear brother's death\n"+
"The memory be green, and that it us befitted\n"+
"To bear our hearts in grief and our whole kingdom\n"+
"To be contracted in one brow of woe,\n"+
"Yet so far hath discretion fought with nature\n"+
"That we with wisest sorrow think on him,\n"+
"Together with remembrance of ourselves.\n"+
"Therefore our sometime sister, now our queen,\n"+
"The imperial jointress to this warlike state,\n"+
"Have we, as 'twere with a defeated joy,--\n"+
"With an auspicious and a dropping eye,\n"+
"With mirth in funeral and with dirge in marriage,\n"+
"In equal scale weighing delight and dole,--\n"+
"Taken to wife: nor have we herein barr'd\n"+
"Your better wisdoms, which have freely gone\n"+
"With this affair along. For all, our thanks.\n"+
"Now follows, that you know, young Fortinbras,\n"+
"Holding a weak supposal of our worth,\n"+
"Or thinking by our late dear brother's death\n"+
"Our state to be disjoint and out of frame,\n"+
"Colleagued with the dream of his advantage,\n"+
"He hath not fail'd to pester us with message,\n"+
"Importing the surrender of those lands\n"+
"Lost by his father, with all bonds of law,\n"+
"To our most valiant brother. So much for him.\n"+
"Now for ourself and for this time of meeting:\n"+
"Thus much the business is: we have here writ\n"+
"To Norway, uncle of young Fortinbras,--\n"+
"Who, impotent and bed-rid, scarcely hears\n"+
"Of this his nephew's purpose,--to suppress\n"+
"His further gait herein; in that the levies,\n"+
"The lists and full proportions, are all made\n"+
"Out of his subject: and we here dispatch\n"+
"You, good Cornelius, and you, Voltimand,\n"+
"For bearers of this greeting to old Norway;\n"+
"Giving to you no further personal power\n"+
"To business with the king, more than the scope\n"+
"Of these delated articles allow.\n"+
"Farewell, and let your haste commend your duty.",
        "The quality of mercy is not strain'd,\n"+
"It droppeth as the gentle rain from heaven\n"+
"Upon the place beneath: it is twice blest;\n"+
"It blesseth him that gives and him that takes:\n"+
"'Tis mightiest in the mightiest: it becomes\n"+
"The throned monarch better than his crown;\n"+
"His sceptre shows the force of temporal power,\n"+
"The attribute to awe and majesty,\n"+
"Wherein doth sit the dread and fear of kings;\n"+
"But mercy is above this sceptred sway;\n"+
"It is enthroned in the hearts of kings,\n"+
"It is an attribute to God himself;\n"+
"And earthly power doth then show likest God's\n"+
"When mercy seasons justice. Therefore, Jew,\n"+
"Though justice be thy plea, consider this,\n"+
"That, in the course of justice, none of us\n"+
"Should see salvation: we do pray for mercy;\n"+
"And that same prayer doth teach us all to render\n"+
"The deeds of mercy. I have spoke thus much\n"+
"To mitigate the justice of thy plea;\n"+
"Which if thou follow, this strict court of Venice\n"+
"Must needs give sentence 'gainst the merchant there.",
        "It is Othello's pleasure, our noble and valiant\n"+
"general, that, upon certain tidings now arrived,\n"+
"importing the mere perdition of the Turkish fleet,\n"+
"every man put himself into triumph; some to dance,\n"+
"some to make bonfires, each man to what sport and\n"+
"revels his addiction leads him: for, besides these\n"+
"beneficial news, it is the celebration of his\n"+
"nuptial. So much was his pleasure should be\n"+
"proclaimed. All offices are open, and there is full\n"+
"liberty of feasting from this present hour of five\n"+
"till the bell have told eleven. Heaven bless the\n"+
"isle of Cyprus and our noble general Othello!"
    };
}
