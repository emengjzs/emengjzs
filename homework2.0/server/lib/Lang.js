exports.namedLambda = (name, lambda) => ({ [name]: lambda});

// expect that the callback argument is in the last arg of fn 
exports.currrifyCallback = fn => (...args) => callback => fn(...args, callback);

