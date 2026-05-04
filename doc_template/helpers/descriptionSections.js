/**
 * Parse the concatenated description markdown (info.description) into a tree
 * of top-level sections (`# `) and their direct subsections (`## `).
 *
 * Each entry: { title, id, subsections: [{ title, id }] }.
 *
 * `id` matches the slug that marked's renderer produces for headings:
 *   raw.toLowerCase().replace(/[^\w]+/g, '-')
 * so links like `#server-url` resolve to the rendered <h2 id="server-url">.
 */

function slug(text) {
  return String(text).toLowerCase().replace(/[^\w]+/g, '-')
}

module.exports = function(value) {
  if (!value) return []

  var lines = String(value).split('\n')
  var sections = []
  var current = null
  var inFence = false

  for (var i = 0; i < lines.length; i++) {
    var line = lines[i]

    if (/^\s*```/.test(line)) {
      inFence = !inFence
      continue
    }
    if (inFence) continue

    var h1 = /^#\s+(.+?)\s*#*\s*$/.exec(line)
    if (h1) {
      current = { title: h1[1], id: slug(h1[1]), subsections: [] }
      sections.push(current)
      continue
    }
    var h2 = /^##\s+(.+?)\s*#*\s*$/.exec(line)
    if (h2 && current) {
      current.subsections.push({ title: h2[1], id: slug(h2[1]) })
    }
  }

  return sections
}
