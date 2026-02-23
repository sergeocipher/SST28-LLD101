public abstract class Exporter {
    // implied "contract" but not enforced (smell)
    public final ExportResult export(ExportRequest req) {
        if (req == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        return doExport(req);
    }

    protected abstract ExportResult doExport(ExportRequest req);


}

// Before:

// Subclass fully controlled behavior.

// After:

// Base class enforces:

// Non-null request

// Uniform entry point

// Clear contract

// Subclasses only implement formatting.